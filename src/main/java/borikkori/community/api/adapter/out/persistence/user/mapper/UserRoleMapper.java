package borikkori.community.api.adapter.out.persistence.user.mapper;

import borikkori.community.api.adapter.in.web.user.response.UserResponse;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserRoleEntity;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.entity.UserRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    // 영속성 모델 -> 도메인 모델
    UserRole toDomain(UserRoleEntity entity);

    // 도메인 모델 -> 영속성 모델
    UserRoleEntity toEntity(UserRole domain);

}
