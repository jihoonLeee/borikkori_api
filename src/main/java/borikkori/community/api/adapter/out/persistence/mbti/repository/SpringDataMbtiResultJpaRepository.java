package borikkori.community.api.adapter.out.persistence.mbti.repository;

import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiResultEntity;
import borikkori.community.api.common.enums.MbtiType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataMbtiResultJpaRepository extends JpaRepository<MbtiResultEntity,Long> {

    // Repository 메서드 정의
    Page<MbtiResultEntity> findAllByOrderByCountDesc(Pageable pageable);

    // 특정 MbtiType에 해당하는 결과 조회
    Optional<MbtiResultEntity> findByType(MbtiType type);
}
