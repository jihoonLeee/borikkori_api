package borikkori.community.api.adapter.out.persistence.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import borikkori.community.api.adapter.out.persistence.user.entity.UserRoleEntity;

public interface SpringDataUserRoleJpaRepository extends JpaRepository<UserRoleEntity, Long> {
}
