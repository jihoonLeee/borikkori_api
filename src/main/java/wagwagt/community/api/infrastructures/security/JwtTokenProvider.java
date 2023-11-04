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
import java.util.Date;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.secret.key}")
    private String salt;

    private Key secretKey;

    private final long exp = 1000L*60*60;// 1시간

    private final JpaUserDetailService userDetailsService;

    @PostConstruct
    protected void init(){
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 토큰 생성
     */
    public String createToken(String account, List<Authority> roles){
        Claims claims = Jwts.claims().setSubject(account);
        claims.put("roles",roles);
        Date now= new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+exp))
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
         return request.getHeader("Authorization");
     }

     /**
      * 토큰 검증
      * */
     public boolean validateToken(String token){
         try{
             //Bearer검증
             if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                 return false;
             }else{
                 token = token.split(" ")[1].trim();
             }
             Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
             // 만료되면 False
             return !claims.getBody().getExpiration().before(new Date());
         }catch (Exception e){
             return false;
         }
     }

}
