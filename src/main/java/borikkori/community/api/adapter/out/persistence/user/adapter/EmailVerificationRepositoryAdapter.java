package borikkori.community.api.adapter.out.persistence.user.adapter;

import org.springframework.stereotype.Repository;

import borikkori.community.api.adapter.out.persistence.user.entity.EmailVerificationEntity;
import borikkori.community.api.adapter.out.persistence.user.repository.SpringDataEmailVerificationJpaRepository;
import borikkori.community.api.common.exeptions.EntityNotFoundException;
import borikkori.community.api.domain.user.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmailVerificationRepositoryAdapter implements EmailVerificationRepository {

	private final SpringDataEmailVerificationJpaRepository emailVerificationJpaRepository;

	@Override
	public void saveVerification(EmailVerificationEntity emailVerificationEntity) {
		emailVerificationJpaRepository.save(emailVerificationEntity);
	}

	@Override
	public EmailVerificationEntity findLatestVerificationByEmail(String email) {
		return emailVerificationJpaRepository.findTopByEmailOrderByRegDateDesc(email)
			.orElseThrow(() -> new EntityNotFoundException("No email verification found for email: " + email));
	}
}
