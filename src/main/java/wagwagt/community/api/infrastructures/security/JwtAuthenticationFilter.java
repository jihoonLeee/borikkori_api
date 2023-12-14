package wagwagt.community.api.infrastructures.security;

import io.lettuce.core.RedisException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import wagwagt.community.api.dto.RefreshToken;
import wagwagt.community.api.entities.User;
import wagwagt.community.api.infrastructures.config.RedisConfig;
import wagwagt.community.api.repositories.AuthorityRepository;
import wagwagt.community.api.repositories.RefreshTokenRepository;
import wagwagt.community.api.repositories.UserRepository;

import java.io.IOException;

@Description("JWT가 유효성을 검증하는 필터")
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    User user;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{
        try {
            String token = jwtTokenProvider.resolveToken(request);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            log.error("Failed to process authentication", e);
        }

        filterChain.doFilter(request, response);

    }



}
