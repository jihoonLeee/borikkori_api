package borikkori.community.api.adapter.out.persistence.post.repository;

import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.common.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpringDataPostJpaRepository extends JpaRepository<PostEntity, Long> {

    @Query("SELECT p FROM PostEntity p WHERE p.user = :user AND p.postStatus = :postStatus ORDER BY p.id DESC")
    Optional<PostEntity> findLatestTemporaryPost(@Param("user") UserEntity user, @Param("postStatus") PostStatus postStatus);
}