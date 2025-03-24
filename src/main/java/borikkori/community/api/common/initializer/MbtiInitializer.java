package borikkori.community.api.common.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import borikkori.community.api.common.enums.MbtiType;
import borikkori.community.api.domain.mbti.entity.MbtiStatistics;
import borikkori.community.api.domain.mbti.repository.MbtiRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MbtiInitializer implements CommandLineRunner {
	private final MbtiRepository mbtiRepository;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		if (mbtiRepository.countMbtiResult() == 0) { // MBTI가 없을 경우만 초기화
			mbtiRepository.saveMbtiResult(new MbtiStatistics(MbtiType.ENFJ, 0L));
			mbtiRepository.saveMbtiResult(new MbtiStatistics(MbtiType.ENFP, 0L));
			mbtiRepository.saveMbtiResult(new MbtiStatistics(MbtiType.ENTJ, 0L));
			mbtiRepository.saveMbtiResult(new MbtiStatistics(MbtiType.ENTP, 0L));
			mbtiRepository.saveMbtiResult(new MbtiStatistics(MbtiType.ESFJ, 0L));
			mbtiRepository.saveMbtiResult(new MbtiStatistics(MbtiType.ESFP, 0L));
			mbtiRepository.saveMbtiResult(new MbtiStatistics(MbtiType.ESTP, 0L));
			mbtiRepository.saveMbtiResult(new MbtiStatistics(MbtiType.ESFP, 0L));
			mbtiRepository.saveMbtiResult(new MbtiStatistics(MbtiType.INFJ, 0L));
			mbtiRepository.saveMbtiResult(new MbtiStatistics(MbtiType.INFP, 0L));
			mbtiRepository.saveMbtiResult(new MbtiStatistics(MbtiType.INTJ, 0L));
			mbtiRepository.saveMbtiResult(new MbtiStatistics(MbtiType.INTP, 0L));
			mbtiRepository.saveMbtiResult(new MbtiStatistics(MbtiType.ISFJ, 0L));
			mbtiRepository.saveMbtiResult(new MbtiStatistics(MbtiType.ISFP, 0L));
			mbtiRepository.saveMbtiResult(new MbtiStatistics(MbtiType.ISTP, 0L));
			mbtiRepository.saveMbtiResult(new MbtiStatistics(MbtiType.ISFP, 0L));
		}
	}
}
