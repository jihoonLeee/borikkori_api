package borikkori.community.api.adapter.in.web.mbti.response;

import borikkori.community.api.common.enums.MbtiType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MbtiResultResponse {

	private MbtiType type;

	private Long count;
}
