package wagwagt.community.api.infrastructures.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import wagwagt.community.api.infrastructures.config.RedisConfig;

import java.io.IOException;

@Description("JWT가 유효성을 검증하는 필터")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{
        String token = jwtTokenProvider.resolveToken(request);
        
        if(token != null && jwtTokenProvider.validateToken(token)){
            // access 토큰 체크

            token = token.split(" ")[1].trim();

            Authentication auth= jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request,response);
    }



}
