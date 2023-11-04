package wagwagt.community.api.dtos;

public class JwtDto {

    private String grantType;  //JWT 대한 인증 타입 (Bearer)
    private String accessToken;
    private String refreshToken;
}
