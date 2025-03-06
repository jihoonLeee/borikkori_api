package borikkori.community.api.infrastructures.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import borikkori.community.api.domain.user.interfaces.dto.RefreshToken;
import borikkori.community.api.domain.user.entities.Authority;
import borikkori.community.api.infrastructures.provider.JwtTokenProvider;
import borikkori.community.api.domain.user.interfaces.repositories.RefreshTokenRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Description("JWT 유효성 검증 필터")
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${cookie.domain}")
    private String cookieDomain;

    @Value("${cookie.secure}")
    private boolean cookieSecure;

    @Value("${cookie.path}")
    private String cookiePath;

    @Value("${cookie.max-age}")
    private int cookieMaxAge;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtTokenProvider.resolveToken(request);
            if (token != null) {
                handleTokenAuthentication(token, request, response);
            }
        } catch (Exception e) {
            log.error("Authentication failed", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void handleTokenAuthentication(String token, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            refreshAndAuthenticateToken(token, response);
        }
    }

    private void refreshAndAuthenticateToken(String token, HttpServletResponse response) throws IOException {
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByAccessToken(token);

        if (refreshTokenOptional.isPresent()) {
            RefreshToken refreshToken = refreshTokenOptional.get();
            if (jwtTokenProvider.validateToken(refreshToken.getRefreshToken())) {
                Claims userAuth = jwtTokenProvider.getInfo(token);
                String accessToken = jwtTokenProvider.createToken(
                        userAuth.getSubject(),
                        (String) userAuth.get("nickName"),
                        (List<Authority>) userAuth.get("role")
                );
                refreshTokenRepository.save(refreshToken);
                setAccessTokenInCookie(response, accessToken);
            }
        }
    }

    private void setAccessTokenInCookie(HttpServletResponse response, String accessToken) {
        ResponseCookie cookie = ResponseCookie.from("access_token", accessToken)
                .path(cookiePath)
                .sameSite("Strict")
                .httpOnly(true)
                .secure(cookieSecure)
                .domain(cookieDomain)
                .maxAge(cookieMaxAge)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }
}
