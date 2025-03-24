package borikkori.community.api.domain.mbti.entity;

import borikkori.community.api.common.enums.MbtiType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MbtiStatistics {
	private final MbtiType type;
	private final Long count;
}
