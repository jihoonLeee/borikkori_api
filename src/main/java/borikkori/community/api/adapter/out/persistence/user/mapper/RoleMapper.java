package borikkori.community.api.adapter.out.persistence.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.common.enums.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

	@Named("toDomainRole")
	default Role toDomain(RoleEntity roleEntity) {
		return roleEntity == null ? null : roleEntity.getRole();
	}

	@Named("toEntityRole")
	default RoleEntity toEntity(Role role) {
		return role == null ? null : RoleEntity.create(role);
	}
}
