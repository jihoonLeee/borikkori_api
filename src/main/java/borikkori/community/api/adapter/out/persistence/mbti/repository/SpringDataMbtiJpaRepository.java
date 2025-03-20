package borikkori.community.api.adapter.out.persistence.mbti.repository;

import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.EmailVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataMbtiJpaRepository extends JpaRepository<MbtiEntity,Long> {


}
