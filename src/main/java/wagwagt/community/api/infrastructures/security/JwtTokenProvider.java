package wagwagt.community.api.infrastructures.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import wagwagt.community.api.entities.Authority;
import wagwagt.community.api.usecases.CustomUserDetail;
import wagwagt.community.api.usecases.JpaUserDetailService;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;


@Component
@RequiredArgsConstructor
@PropertySource("classpath:jwt.yml")
public class JwtTokenProvider {
    @Value("${secret-key}")
    private String salt;

    private Key secretKey;

    @Value("${expiration-minutes}")
    private long exp;

    @Value("${refresh-expiration-hours}")
    private long refreshExp;

    private final JpaUserDetailService userDetailsService;

    @PostConstruct
    protected void init(){
        secretKey = Keys.hmacShaKeyFor(salt.getBytes());
    }

    /**
     * 토큰 생성
     */
    public String createToken(String account, Authority role){
        Claims claims = Jwts.claims().setSubject(account);
        claims.put("role",role);
        Date now= new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(Date.from(Instant.now().plus(exp, ChronoUnit.MINUTES)))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
    /**
     * 리프래시 토큰 생성
     */
    public String createRefreshToken(String account, Authority role){
        Claims claims = Jwts.claims().setSubject(account);
        claims.put("role",role);
        Date now= new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(Date.from(Instant.now().plus(refreshExp, ChronoUnit.HOURS)))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
    /**
     * 권한 정보 획득
     */
     public Authentication getAuthentication(String token){
         UserDetails userDetails = userDetailsService.loadUserByUsername(this.getAccount(token));
         return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
     }

    /**
     * 토큰에 담겨있는 유저 account 획득
     */
     public String getAccount(String token){
         return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
     }

     /**
      * Authorization Header를 통해 인증
      * */
     public String resolveToken(HttpServletRequest request){
         return request.getHeader("Authorization").substring(7);
     }

     /**
      * 토큰 검증
      * */
     public boolean validateToken(String token){
         try{
             //Bearer검증
             if (token.contains("BEARER")&&!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                 return false;
             }else{
                 token = token.split(" ")[0].trim();
             }
             Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
             // 만료되면 False
             return !claims.getBody().getExpiration().before(new Date());
         }catch (Exception e){
             return false;
         }
     }

}
