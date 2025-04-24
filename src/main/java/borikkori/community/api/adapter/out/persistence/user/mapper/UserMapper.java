package borikkori.community.api.adapter.out.persistence.user.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import borikkori.community.api.adapter.in.web.user.response.UserResponse;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.common.enums.Role;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.vo.UserId;

@Mapper(componentModel = "spring")
public interface UserMapper {

	// 영속성 모델 -> 도메인 모델
	@Mapping(target = "mbtiResult", source = "mbtiEntity.result")
	@Mapping(target = "roles", expression = "java(mapRoles(entity))")
	// 핵심 부분
	User toDomain(UserEntity entity);

	// 도메인 모델 -> 영속성 모델
	@Mapping(target = "mbtiEntity", ignore = true)
	@Mapping(target = "userRoles", ignore = true)
	UserEntity toEntity(User domain);

	// 도메인 모델 -> 응답 DTO
	@Mapping(target = "mbtiType", source = "mbtiResult")
	UserResponse toResponse(User user);

	default UserId map(Long value) {
		return value == null ? null : new UserId(value);
	}

	default Long map(UserId userId) {
		return userId == null ? null : userId.getId();
	}

	default List<Role> mapRoles(UserEntity entity) {
		if (entity == null || entity.getUserRoles() == null)
			return List.of();
		return entity.getUserRoles().stream()
			.map(userRoleEntity -> userRoleEntity.getRole().getRole())
			.collect(Collectors.toList());
	}

}
