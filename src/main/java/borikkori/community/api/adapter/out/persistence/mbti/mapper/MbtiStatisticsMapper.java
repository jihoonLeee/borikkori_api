package borikkori.community.api.adapter.out.persistence.mbti.mapper;

import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiResultEntity;
import borikkori.community.api.domain.mbti.entity.MbtiStatistics;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MbtiStatisticsMapper {

    // 엔티티 -> 도메인
    MbtiStatistics toDomain(MbtiResultEntity entity);

    // 도메인 -> 엔티티
    MbtiResultEntity toEntity(MbtiStatistics entity);
}
