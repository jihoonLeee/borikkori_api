package wagwagt.community.api.responses;

import lombok.Builder;
import lombok.Getter;
import wagwagt.community.api.enums.MbtiType;

@Builder
@Getter
public class MbtiResultResponse {

    private MbtiType type;

    private Long count;
}
