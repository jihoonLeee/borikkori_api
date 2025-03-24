package borikkori.community.api.adapter.out.persistence.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import borikkori.community.api.adapter.out.persistence.post.entity.CommentLikeEntity;

public interface SpringDataCommentLikeJpaRepository extends JpaRepository<CommentLikeEntity, Long> {

}
