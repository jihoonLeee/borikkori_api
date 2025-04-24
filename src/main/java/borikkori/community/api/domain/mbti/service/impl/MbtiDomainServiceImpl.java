package borikkori.community.api.domain.mbti.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import borikkori.community.api.common.enums.MbtiType;
import borikkori.community.api.domain.mbti.entity.MbtiStatistics;
import borikkori.community.api.domain.mbti.entity.UserMbti;
import borikkori.community.api.domain.mbti.service.MbtiDomainService;

@Service
public class MbtiDomainServiceImpl implements MbtiDomainService {

	/**
	 * 사용자 MBTI 결과 도메인 객체 생성
	 */
	public UserMbti createUserMbti(MbtiType result, String testName) {
		// 도메인 규칙에 따라 MBTI 결과 생성
		return new UserMbti(
			null,
			result,
			LocalDateTime.now(),
			testName
		);
	}

	/**
	 * MBTI 통계 업데이트 로직 (해당 유형의 카운트를 증가시킴)
	 */
	public MbtiStatistics updateStatistics(MbtiType type, MbtiStatistics currentStats) {
		long updatedCount = currentStats.getCount() + 1;
		return new MbtiStatistics(type, updatedCount);
	}
}
