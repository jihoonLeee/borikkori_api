package borikkori.community.api.adapter.out.persistence.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import borikkori.community.api.adapter.out.persistence.user.entity.EmailVerificationEntity;

public interface SpringDataEmailVerificationJpaRepository extends JpaRepository<EmailVerificationEntity, Long> {
	// 이메일로 검색하여 최신 인증 기록을 조회
	Optional<EmailVerificationEntity> findTopByEmailOrderByRegDateDesc(String email);

}
