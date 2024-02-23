package wagwagt.community.api.infrastructures.security;

import io.jsonwebtoken.Claims;
import io.lettuce.core.RedisException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import wagwagt.community.api.dto.RefreshToken;
import wagwagt.community.api.entities.Authority;
import wagwagt.community.api.entities.User;
import wagwagt.community.api.infrastructures.config.RedisConfig;
import wagwagt.community.api.repositories.AuthorityRepository;
import wagwagt.community.api.repositories.RefreshTokenRepository;
import wagwagt.community.api.repositories.UserRepository;

import java.io.IOException;
import java.util.Optional;

@Description("JWT가 유효성을 검증하는 필터")
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final String MAIN_URL = "https://www.wagwagt.world";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{
        try {
            String token = jwtTokenProvider.resolveToken(request);
            if (token != null){
                 if(jwtTokenProvider.validateToken(token)) {
                    Authentication auth = jwtTokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }else{
                     Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByAccessToken(token);
                     if (refreshTokenOptional.isPresent()) {
                         RefreshToken refreshToken = refreshTokenOptional.get();
                         if(jwtTokenProvider.validateToken(refreshToken.getRefreshToken())){
                             Claims userAuth = jwtTokenProvider.getInfo(token);
                             String accessToken= jwtTokenProvider.createToken(userAuth.getSubject(),(String) userAuth.get("nickName"),(Authority) userAuth.get("role"));
                             refreshTokenRepository.save(refreshToken);
                             ResponseCookie cookie = ResponseCookie.from("access_token",accessToken)
                                     .path("/")
                                     .sameSite("false")
                                     .httpOnly(true)
                                     .secure(false)
                                     .domain("localhost")
                                     .maxAge(60*30)
                                     .build();
                             response.addHeader("Set-Cookie",cookie.toString());
                         }
                     }
                }
            }
        } catch (Exception e) {
            log.error("Failed to process authentication", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
            return;
        }

        filterChain.doFilter(request, response);
    }




}
