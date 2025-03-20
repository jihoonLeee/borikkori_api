package borikkori.community.api.adapter.out.persistence.user.adapter;

import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.out.persistence.user.mapper.RoleMapper;
import borikkori.community.api.adapter.out.persistence.user.mapper.UserMapper;
import borikkori.community.api.adapter.out.persistence.user.repository.SpringDataRoleJpaRepository;
import borikkori.community.api.adapter.out.persistence.user.repository.SpringDataUserJpaRepository;
import borikkori.community.api.common.enums.Role;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.repository.RoleRepository;
import borikkori.community.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepository {
    private final SpringDataRoleJpaRepository roleJpaRepository;
    private final RoleMapper roleMapper;

    @Override
    public void saveRole(Role role) {
        RoleEntity entity = roleMapper.toEntity(role);
        roleJpaRepository.save(entity);
    }

    @Override
    public Role findRoleById(Long id) {
        RoleEntity entity = roleJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
        return roleMapper.toDomain(entity);
    }

    @Override
    public Optional<Role> findByRole(Role role) {
        Optional<RoleEntity> entityOpt = roleJpaRepository.findByRole(role);
        return entityOpt.map(roleMapper::toDomain);
    }

    @Override
    public long countRole() {
        return roleJpaRepository.count();
    }
}
