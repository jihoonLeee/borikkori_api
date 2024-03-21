package wagwagt.community.api.domain.mbti.interfaces.dto.response;

import lombok.Builder;
import lombok.Getter;
import wagwagt.community.api.common.enums.MbtiType;

@Builder
@Getter
public class MbtiResultResponse {

    private MbtiType type;

    private Long count;
}
