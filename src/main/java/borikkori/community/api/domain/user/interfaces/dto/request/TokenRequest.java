package borikkori.community.api.domain.user.interfaces.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenRequest {

    private String grantType;  //JWT 대한 인증 타입 (Bearer)
    private String accessToken;
    private String refreshToken;
}
