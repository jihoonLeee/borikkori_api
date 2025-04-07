package borikkori.community.api.adapter.out.persistence.user.mapper;

import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserRoleEntity;
import borikkori.community.api.common.enums.MbtiType;
import borikkori.community.api.common.enums.Role;
import borikkori.community.api.common.enums.RoleStatus;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.entity.UserRole;
import borikkori.community.api.domain.user.vo.UserId;
import borikkori.community.api.domain.user.vo.UserRoleId;
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
public class UserRoleMapperImpl implements UserRoleMapper {

    @Override
    public UserRole toDomain(UserRoleEntity entity) {
        if ( entity == null ) {
            return null;
        }

        User user = null;
        Role role = null;
        RoleStatus status = null;
        LocalDateTime regDate = null;
        LocalDateTime updDate = null;
        UserRoleId id = null;

        user = userEntityToUser( entity.getUser() );
        role = entityRoleRole( entity );
        status = entity.getStatus();
        regDate = entity.getRegDate();
        updDate = entity.getUpdDate();
        id = map( entity.getId() );

        UserRole userRole = new UserRole( id, user, role, status, regDate, updDate );

        return userRole;
    }

    @Override
    public UserRoleEntity toEntity(UserRole domain) {
        if ( domain == null ) {
            return null;
        }

        RoleEntity role = null;
        Long id = null;
        LocalDateTime regDate = null;
        LocalDateTime updDate = null;
        RoleStatus status = null;

        role = map( domain.getRole() );
        id = map( domain.getId() );
        regDate = domain.getRegDate();
        updDate = domain.getUpdDate();
        status = domain.getStatus();

        UserEntity user = null;

        UserRoleEntity userRoleEntity = new UserRoleEntity( id, user, role, regDate, updDate, status );

        return userRoleEntity;
    }

    protected User userEntityToUser(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

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

        id = mapUserId( userEntity.getId() );
        name = userEntity.getName();
        email = userEntity.getEmail();
        password = userEntity.getPassword();
        active = userEntity.isActive();
        emailVerified = userEntity.isEmailVerified();
        accountLocked = userEntity.isAccountLocked();
        failedLoginAttempts = userEntity.getFailedLoginAttempts();
        lastLoginDate = userEntity.getLastLoginDate();
        regDate = userEntity.getRegDate();
        updDate = userEntity.getUpdDate();

        List<Role> roles = null;
        MbtiType mbtiResult = null;

        User user = new User( id, name, email, password, active, emailVerified, accountLocked, failedLoginAttempts, lastLoginDate, regDate, updDate, roles, mbtiResult );

        return user;
    }

    private Role entityRoleRole(UserRoleEntity userRoleEntity) {
        if ( userRoleEntity == null ) {
            return null;
        }
        RoleEntity role = userRoleEntity.getRole();
        if ( role == null ) {
            return null;
        }
        Role role1 = role.getRole();
        if ( role1 == null ) {
            return null;
        }
        return role1;
    }
}
