package borikkori.community.api.adapter.out.persistence.mbti.mapper;

import borikkori.community.api.adapter.in.web.mbti.response.MbtiResultResponse;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiResultEntity;
import borikkori.community.api.common.enums.MbtiType;
import borikkori.community.api.domain.mbti.entity.MbtiStatistics;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T19:06:13+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class MbtiStatisticsMapperImpl implements MbtiStatisticsMapper {

    @Override
    public MbtiStatistics toDomain(MbtiResultEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MbtiType type = null;
        Long count = null;

        type = entity.getType();
        count = entity.getCount();

        MbtiStatistics mbtiStatistics = new MbtiStatistics( type, count );

        return mbtiStatistics;
    }

    @Override
    public MbtiResultEntity toEntity(MbtiStatistics domain) {
        if ( domain == null ) {
            return null;
        }

        MbtiResultEntity mbtiResultEntity = new MbtiResultEntity();

        mbtiResultEntity.setType( domain.getType() );

        mbtiResultEntity.setCount( domain.getCount() );

        return mbtiResultEntity;
    }

    @Override
    public MbtiResultResponse toResponse(MbtiStatistics domain) {
        if ( domain == null ) {
            return null;
        }

        MbtiResultResponse.MbtiResultResponseBuilder mbtiResultResponse = MbtiResultResponse.builder();

        mbtiResultResponse.type( domain.getType() );
        mbtiResultResponse.count( domain.getCount() );

        return mbtiResultResponse.build();
    }
}
