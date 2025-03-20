package borikkori.community.api.adapter.out.persistence.mbti.adapter;

import borikkori.community.api.adapter.out.persistence.mbti.repository.SpringDataMbtiJpaRepository;
import borikkori.community.api.adapter.out.persistence.mbti.repository.SpringDataMbtiResultJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiEntity;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiResultEntity;
import borikkori.community.api.common.enums.MbtiType;
import borikkori.community.api.domain.mbti.repository.MbtiRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MbtiRepositoryAdapter implements MbtiRepository {

    private final SpringDataMbtiJpaRepository mbtiJpaRepository;
    private final SpringDataMbtiResultJpaRepository mbtiResultJpaRepository;


    @Override
    public void save(MbtiEntity mbtiEntity) {
        mbtiJpaRepository.save(mbtiEntity);
    }

    @Override
    public List<MbtiResultEntity> findTopX(int topX) {
        return mbtiResultJpaRepository.findTopXByOrderByCountDesc(topX);
    }

    @Override
    public MbtiResultEntity findByResult(MbtiType type) {
        return mbtiResultJpaRepository.findByType(type)
                .orElseThrow(() -> new RuntimeException("No MBTI result found for type: " + type));
    }
}
