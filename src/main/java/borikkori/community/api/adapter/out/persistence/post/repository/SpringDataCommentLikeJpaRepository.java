package borikkori.community.api.adapter.out.persistence.post.repository;

import borikkori.community.api.adapter.out.persistence.post.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCommentLikeJpaRepository extends JpaRepository<CommentLikeEntity,Long> {

}
