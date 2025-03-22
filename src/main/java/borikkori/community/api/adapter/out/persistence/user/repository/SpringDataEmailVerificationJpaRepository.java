package borikkori.community.api.adapter.out.persistence.user.repository;

import borikkori.community.api.adapter.out.persistence.user.entity.EmailVerificationEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataEmailVerificationJpaRepository extends JpaRepository<EmailVerificationEntity,Long> {
    // 이메일로 검색하여 최신 인증 기록을 조회
    Optional<EmailVerificationEntity> findTopByEmailOrderByRegDateDesc(String email);

}
