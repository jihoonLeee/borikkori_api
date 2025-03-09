package borikkori.community.api.adapter.in.web.user.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenRequest {

    private String grantType;  //JWT 대한 인증 타입 (Bearer)
    private String accessToken;
    private String refreshToken;
}
