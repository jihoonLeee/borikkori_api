package borikkori.community.api.adapter.out.persistence.user.repository;

import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUserJpaRepository extends JpaRepository<UserEntity,Long> {

}
