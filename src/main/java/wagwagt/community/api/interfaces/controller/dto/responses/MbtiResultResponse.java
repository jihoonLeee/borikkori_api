package wagwagt.community.api.interfaces.controller.dto.responses;

import lombok.Builder;
import lombok.Getter;
import wagwagt.community.api.entities.domain.enums.MbtiType;

@Builder
@Getter
public class MbtiResultResponse {

    private MbtiType type;

    private Long count;
}
