package borikkori.community.api.adapter.out.persistence.user.repository;

import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserRoleJpaRepository extends JpaRepository<UserRoleEntity,Long> {
}
