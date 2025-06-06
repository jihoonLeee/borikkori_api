package borikkori.community.api.adapter.in.web.user.response;

import borikkori.community.api.common.enums.MbtiType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {
	Long Id;
	@Schema(description = "유저 닉네임")
	String name;

	@Schema(description = "유저 이메일", example = "jihoonn@gmail.com")
	String email;

	@Schema(description = "mbti 결과", example = "ISTJ")
	MbtiType mbtiType;
}
