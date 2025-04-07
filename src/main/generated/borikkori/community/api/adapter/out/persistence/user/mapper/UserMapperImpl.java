package borikkori.community.api.adapter.out.persistence.user.mapper;

import borikkori.community.api.adapter.in.web.user.response.UserResponse;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserRoleEntity;
import borikkori.community.api.common.enums.MbtiType;
import borikkori.community.api.common.enums.Role;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.vo.UserId;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-04T23:58:52+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toDomain(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MbtiType mbtiResult = null;
        UserId id = null;
        String name = null;
        String email = null;
        String password = null;
        boolean active = false;
        boolean emailVerified = false;
        boolean accountLocked = false;
        int failedLoginAttempts = 0;
        LocalDateTime lastLoginDate = null;
        LocalDateTime regDate = null;
        LocalDateTime updDate = null;

        mbtiResult = entityMbtiEntityResult( entity );
        id = map( entity.getId() );
        name = entity.getName();
        email = entity.getEmail();
        password = entity.getPassword();
        active = entity.isActive();
        emailVerified = entity.isEmailVerified();
        accountLocked = entity.isAccountLocked();
        failedLoginAttempts = entity.getFailedLoginAttempts();
        lastLoginDate = entity.getLastLoginDate();
        regDate = entity.getRegDate();
        updDate = entity.getUpdDate();

        List<Role> roles = mapRoles(entity);

        User user = new User( id, name, email, password, active, emailVerified, accountLocked, failedLoginAttempts, lastLoginDate, regDate, updDate, roles, mbtiResult );

        return user;
    }

    @Override
    public UserEntity toEntity(User domain) {
        if ( domain == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String email = null;
        String password = null;
        boolean active = false;
        boolean emailVerified = false;
        boolean accountLocked = false;
        int failedLoginAttempts = 0;
        LocalDateTime lastLoginDate = null;
        LocalDateTime regDate = null;
        LocalDateTime updDate = null;

        id = map( domain.getId() );
        name = domain.getName();
        email = domain.getEmail();
        password = domain.getPassword();
        active = domain.isActive();
        emailVerified = domain.isEmailVerified();
        accountLocked = domain.isAccountLocked();
        failedLoginAttempts = domain.getFailedLoginAttempts();
        lastLoginDate = domain.getLastLoginDate();
        regDate = domain.getRegDate();
        updDate = domain.getUpdDate();

        MbtiEntity mbtiEntity = null;
        List<UserRoleEntity> userRoles = null;

        UserEntity userEntity = new UserEntity( id, name, email, password, active, emailVerified, accountLocked, failedLoginAttempts, lastLoginDate, regDate, updDate, mbtiEntity, userRoles );

        return userEntity;
    }

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.mbtiType( user.getMbtiResult() );
        userResponse.name( user.getName() );
        userResponse.email( user.getEmail() );

        return userResponse.build();
    }

    private MbtiType entityMbtiEntityResult(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }
        MbtiEntity mbtiEntity = userEntity.getMbtiEntity();
        if ( mbtiEntity == null ) {
            return null;
        }
        MbtiType result = mbtiEntity.getResult();
        if ( result == null ) {
            return null;
        }
        return result;
    }
}
