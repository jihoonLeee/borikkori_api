package borikkori.community.api.config.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import borikkori.community.api.application.domain.file.usecase.FileUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class UnusedImageCleaner {

	private final FileUseCase fileUseCase;

	/**
	 * 매일 자정(00:00)에 실행되는 메소드
	 * "0 0 0 * * *"는 초, 분, 시, 일, 월, 요일을 의미
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void cleanUnusedImages() {
		fileUseCase.cleanupFiles();
		log.info("미사용 이미지 정리 작업 완료");
	}
}
