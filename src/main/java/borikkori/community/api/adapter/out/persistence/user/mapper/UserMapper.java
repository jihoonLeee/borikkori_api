package borikkori.community.api.adapter.out.persistence.user.mapper;

import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.application.user.dto.UserDto;

public class UserMapper {
    public static UserDto toDto(UserEntity entity) {
        return new UserDto(entity.getId(), entity.getName(), entity.getEmail());
    }

    public static UserEntity toEntity(UserDto dto) {
        return new UserEntity(dto.getId(), dto.getName(), dto.getEmail());
    }
}