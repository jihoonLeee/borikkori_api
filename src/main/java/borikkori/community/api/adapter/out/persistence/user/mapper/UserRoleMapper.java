package borikkori.community.api.adapter.out.persistence.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserRoleEntity;
import borikkori.community.api.common.enums.Role;
import borikkori.community.api.domain.user.entity.UserRole;
import borikkori.community.api.domain.user.vo.UserId;
import borikkori.community.api.domain.user.vo.UserRoleId;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

	// 영속성 모델 -> 도메인 모델
	@Mapping(target = "user", source = "entity.user")
	@Mapping(target = "role", source = "entity.role.role") // 엔티티의 RoleEntity를 도메인의 Role enum으로 변환 필요
	@Mapping(target = "status", source = "entity.status")
	@Mapping(target = "regDate", source = "entity.regDate")
	@Mapping(target = "updDate", source = "entity.updDate")
	UserRole toDomain(UserRoleEntity entity);

	// 도메인 모델 -> 영속성 모델
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "role", source = "role")
	UserRoleEntity toEntity(UserRole domain);

	default RoleEntity map(Role role) {
		if (role == null)
			return null;
		return RoleEntity.create(role);
	}

	default UserRoleId map(Long value) {
		return value == null ? null : new UserRoleId(value);
	}

	default Long map(UserRoleId userRoleId) {
		return userRoleId == null ? null : userRoleId.getId();
	}

	default UserId mapUserId(Long value) {
		return value == null ? null : new UserId(value);
	}

	default Long mapUserId(UserId userId) {
		return userId == null ? null : userId.getId();
	}
}
