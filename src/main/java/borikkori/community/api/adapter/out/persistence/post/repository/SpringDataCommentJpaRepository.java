package borikkori.community.api.adapter.out.persistence.post.repository;

import borikkori.community.api.adapter.out.persistence.post.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataCommentJpaRepository extends JpaRepository<CommentEntity,Long> {
    // 페이징 정보를 포함하여 주어진 게시글 ID에 해당하는 댓글들을 조회합니다.
    Page<CommentEntity> findByPostId(Long postId, Pageable pageable);

    // 주어진 게시글 ID에 해당하는 댓글의 총 개수를 반환합니다.
    long countByPostId(Long postId);
}
