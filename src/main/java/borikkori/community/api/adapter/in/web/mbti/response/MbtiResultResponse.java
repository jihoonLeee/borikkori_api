package borikkori.community.api.adapter.in.web.mbti.response;

import lombok.Builder;
import lombok.Getter;
import borikkori.community.api.common.enums.MbtiType;

@Builder
@Getter
public class MbtiResultResponse {

    private MbtiType type;

    private Long count;
}
