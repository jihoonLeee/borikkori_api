package borikkori.community.api.adapter.out.persistence.mbti.adapter;

import borikkori.community.api.adapter.out.persistence.mbti.mapper.MbtiMapper;
import borikkori.community.api.adapter.out.persistence.mbti.mapper.MbtiStatisticsMapper;
import borikkori.community.api.adapter.out.persistence.mbti.repository.SpringDataMbtiJpaRepository;
import borikkori.community.api.adapter.out.persistence.mbti.repository.SpringDataMbtiResultJpaRepository;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.domain.mbti.entity.MbtiStatistics;
import borikkori.community.api.domain.mbti.entity.UserMbti;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiEntity;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiResultEntity;
import borikkori.community.api.common.enums.MbtiType;
import borikkori.community.api.domain.mbti.repository.MbtiRepository;


@Repository
@RequiredArgsConstructor
public class MbtiRepositoryAdapter implements MbtiRepository {

    private final SpringDataMbtiJpaRepository mbtiJpaRepository;
    private final SpringDataMbtiResultJpaRepository mbtiResultJpaRepository;
    private final MbtiMapper mbtiMapper;
    private final MbtiStatisticsMapper mbtiStatisticsMapper;

    @Override
    public void saveUserMbti(UserMbti userMbti) {
        MbtiEntity mbtiEntity = mbtiMapper.toEntity(userMbti);
        mbtiJpaRepository.save(mbtiEntity);
    }

    @Override
    public void saveMbtiResult(MbtiStatistics mbtiStatistics) {
        MbtiResultEntity mbtiResultEntity = mbtiStatisticsMapper.toEntity(mbtiStatistics);
        mbtiResultJpaRepository.save(mbtiResultEntity);
    }

    @Override
    public Page<MbtiStatistics> findTopX(int topX) {
        Pageable pageable = PageRequest.of(0, topX);
        Page<MbtiResultEntity> mbtiResultEntityPage = mbtiResultJpaRepository.
                findAllByOrderByCountDesc(pageable);
        return mbtiResultEntityPage.map(mbtiStatisticsMapper::toDomain);
    }

    @Override
    public MbtiStatistics findByResult(MbtiType type) {
        MbtiResultEntity entity = mbtiResultJpaRepository.findByType(type)
                .orElseThrow(() -> new RuntimeException("No MBTI result found for type: " + type));
        return mbtiStatisticsMapper.toDomain(entity);
    }

    @Override
    public long countMbtiResult() {
        return mbtiResultJpaRepository.count();
    }
}
