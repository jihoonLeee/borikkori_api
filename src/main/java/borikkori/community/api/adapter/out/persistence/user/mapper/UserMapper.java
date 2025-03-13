package borikkori.community.api.adapter.out.persistence.user.mapper;

import borikkori.community.api.adapter.in.web.user.response.UserResponse;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // 영속성 모델 -> 도메인 모델
    @Mapping(target = "mbti",source = "mbtiEntity.result")
    User toDomain(UserEntity entity);

    // 도메인 모델 -> 영속성 모델
    @Mapping(target = "mbtiEntity.result", source = "mbtiResult")
    UserEntity toEntity(User domain);

    // 도메인 모델 -> 응답 DTO
    @Mapping(target = "mbtiType",source = "mbtiResult")
    UserResponse toResponse(User domain);

}
