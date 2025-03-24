package borikkori.community.api.domain.mbti.repository;

import org.springframework.data.domain.Page;

import borikkori.community.api.common.enums.MbtiType;
import borikkori.community.api.domain.mbti.entity.MbtiStatistics;
import borikkori.community.api.domain.mbti.entity.UserMbti;

public interface MbtiRepository {

	void saveUserMbti(UserMbti userMbti);

	void saveMbtiResult(MbtiStatistics mbtiStatistics);

	Page<MbtiStatistics> findTopX(int X);

	MbtiStatistics findByResult(MbtiType type);

	long countMbtiResult();
}
