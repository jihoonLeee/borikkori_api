package borikkori.community.api.adapter.out.persistence.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeEntity;

public interface SpringDataPostLikeJpaRepository extends JpaRepository<PostLikeEntity, Long> {

}
