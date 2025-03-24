package borikkori.community.api.adapter.out.persistence.post.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.common.enums.PostStatus;

public interface SpringDataPostJpaRepository extends JpaRepository<PostEntity, Long> {

	@Query("SELECT p FROM PostEntity p WHERE p.user = :user AND p.postStatus = :postStatus ORDER BY p.id DESC")
	Optional<PostEntity> findLatestTemporaryPost(
		@Param("user") UserEntity user,
		@Param("postStatus") PostStatus postStatus);

	@Query("SELECT p.id FROM PostEntity p WHERE p.regDate > :regDate ORDER BY p.regDate ASC limit 1")
	Long getNextPostId(@Param("regDate") LocalDateTime regDate);

	@Query("SELECT p.id FROM PostEntity p WHERE p.regDate < :regDate ORDER BY p.regDate DESC limit 1")
	Long getPrevPostId(@Param("regDate") LocalDateTime regDate);

}
