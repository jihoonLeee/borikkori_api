package borikkori.community.api.domain.mbti.interfaces.dto.response;

import lombok.Builder;
import lombok.Getter;
import borikkori.community.api.common.enums.MbtiType;

@Builder
@Getter
public class MbtiResultResponse {

    private MbtiType type;

    private Long count;
}
