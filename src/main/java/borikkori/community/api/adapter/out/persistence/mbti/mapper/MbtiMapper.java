package borikkori.community.api.adapter.out.persistence.mbti.mapper;

import borikkori.community.api.adapter.in.web.mbti.response.MbtiResultResponse;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiEntity;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiResultEntity;
import borikkori.community.api.domain.mbti.entity.UserMbti;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MbtiMapper {

    // 엔티티 -> 도메인
    UserMbti toDomain(MbtiEntity entity);

    // 도메인 -> 엔티티
    MbtiEntity toEntity(UserMbti entity);

}
