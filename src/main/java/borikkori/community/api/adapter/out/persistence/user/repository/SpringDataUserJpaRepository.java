package borikkori.community.api.adapter.out.persistence.user.repository;

import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpringDataUserJpaRepository extends JpaRepository<UserEntity,Long> {
    @Query("SELECT u FROM UserEntity u " +
            "LEFT JOIN FETCH u.userRoles ur " +
            "LEFT JOIN FETCH ur.role " +
            "WHERE u.email = :email")
    Optional<UserEntity> findWithRolesByEmail(@Param("email") String email);

}
