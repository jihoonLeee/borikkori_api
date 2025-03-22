package borikkori.community.api.adapter.out.persistence.user.adapter;

import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserRoleEntity;
import borikkori.community.api.adapter.out.persistence.user.mapper.UserMapper;
import borikkori.community.api.adapter.out.persistence.user.mapper.UserRoleMapper;
import borikkori.community.api.adapter.out.persistence.user.repository.SpringDataRoleJpaRepository;
import borikkori.community.api.adapter.out.persistence.user.repository.SpringDataUserJpaRepository;
import borikkori.community.api.adapter.out.persistence.user.repository.SpringDataUserRoleJpaRepository;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.entity.UserRole;
import borikkori.community.api.domain.user.repository.UserRepository;
import borikkori.community.api.domain.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRoleRepositoryAdapter implements UserRoleRepository {

    private final SpringDataUserRoleJpaRepository userRoleJpaRepository;
    private final SpringDataUserJpaRepository userJpaRepository;
    private final SpringDataRoleJpaRepository roleJpaRepository;

    @Override
    public void saveUserRole(UserRole userRole) {
        UserEntity userEntity = userJpaRepository.findById(userRole.getUser().getId().getId())
                .orElseThrow(() -> new IllegalArgumentException("User가 존재하지 않습니다."));
        RoleEntity roleEntity = roleJpaRepository.findByRole(userRole.getRole())
                .orElseThrow(() -> new IllegalArgumentException("Role이 존재하지 않습니다."));
        UserRoleEntity userRoleEntity = UserRoleEntity.create(userEntity, roleEntity, userRole.getStatus());
        userRoleJpaRepository.save(userRoleEntity);
    }
}
