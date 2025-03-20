package borikkori.community.api.adapter.out.persistence.mbti.repository;

import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiResultEntity;
import borikkori.community.api.common.enums.MbtiType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataMbtiResultJpaRepository extends JpaRepository<MbtiResultEntity,Long> {

    // 상위 X개의 결과를 내림차순 정렬로 조회
    List<MbtiResultEntity> findTopXByOrderByCountDesc(int X);

    // 특정 MbtiType에 해당하는 결과 조회
    Optional<MbtiResultEntity> findByType(MbtiType type);
}
