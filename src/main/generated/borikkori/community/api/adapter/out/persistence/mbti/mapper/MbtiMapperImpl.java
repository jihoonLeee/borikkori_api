package borikkori.community.api.adapter.out.persistence.mbti.mapper;

import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.common.enums.MbtiType;
import borikkori.community.api.domain.mbti.entity.UserMbti;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-04T23:58:52+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class MbtiMapperImpl implements MbtiMapper {

    @Override
    public UserMbti toDomain(MbtiEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        MbtiType result = null;
        LocalDateTime testDate = null;
        String testName = null;

        id = entity.getId();
        result = entity.getResult();
        testDate = entity.getTestDate();
        testName = entity.getTestName();

        UserMbti userMbti = new UserMbti( id, result, testDate, testName );

        return userMbti;
    }

    @Override
    public MbtiEntity toEntity(UserMbti entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String testName = null;
        MbtiType result = null;
        LocalDateTime testDate = null;

        id = entity.getId();
        testName = entity.getTestName();
        result = entity.getResult();
        testDate = entity.getTestDate();

        UserEntity user = null;

        MbtiEntity mbtiEntity = new MbtiEntity( id, user, testName, result, testDate );

        return mbtiEntity;
    }
}
