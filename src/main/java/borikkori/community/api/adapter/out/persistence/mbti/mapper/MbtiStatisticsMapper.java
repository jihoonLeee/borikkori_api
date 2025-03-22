package borikkori.community.api.adapter.out.persistence.mbti.mapper;

import borikkori.community.api.adapter.in.web.mbti.response.MbtiResultResponse;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiResultEntity;
import borikkori.community.api.domain.mbti.entity.MbtiStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MbtiStatisticsMapper {

    // 엔티티 -> 도메인
    MbtiStatistics toDomain(MbtiResultEntity entity);

    // 도메인 -> 엔티티
    @Mapping(target = "count", expression = "java(domain.getCount())")
    @Mapping(target = "type", source = "type")
    MbtiResultEntity toEntity(MbtiStatistics domain);

    // 도메인 -> Response
    MbtiResultResponse toResponse(MbtiStatistics domain);
}
