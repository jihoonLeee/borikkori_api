package borikkori.community.api.domain.mbti.entity;

import borikkori.community.api.common.enums.MbtiType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserMbti {
    private final Long id;
    private final MbtiType result;
    private final LocalDateTime testDate;
    private final String testName;

}
