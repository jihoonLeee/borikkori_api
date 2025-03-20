package borikkori.community.api.adapter.out.persistence.post.adapter;

import borikkori.community.api.adapter.out.persistence.post.entity.CommentEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.CommentLikeEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.adapter.out.persistence.post.mapper.CommentLikeMapper;
import borikkori.community.api.adapter.out.persistence.post.mapper.CommentMapper;
import borikkori.community.api.adapter.out.persistence.post.repository.SpringDataCommentJpaRepository;
import borikkori.community.api.adapter.out.persistence.post.repository.SpringDataCommentLikeJpaRepository;
import borikkori.community.api.domain.post.entity.Comment;
import borikkori.community.api.domain.post.entity.CommentLike;
import borikkori.community.api.domain.post.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryAdapter implements CommentRepository {

    private final SpringDataCommentJpaRepository commentJpaRepository;
    private final SpringDataCommentLikeJpaRepository commentLikeJpaRepository;
    private final CommentMapper commentMapper;
    private final CommentLikeMapper commentLikeMapper;

    @Override
    public void saveComment(Comment comment) {
        CommentEntity commentEntity = commentMapper.toEntity(comment);
        commentJpaRepository.save(commentEntity);
    }

    @Override
    public Comment findCommentById(Long id) {
        CommentEntity commentEntity = commentJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
        return commentMapper.toDomain(commentEntity);
    }

    @Override
    public Optional<List<Comment>> findCommentsByPostId(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);  // 페이지 번호가 1부터 시작한다고 가정
        Page<CommentEntity> commentPage = commentJpaRepository.findByPostId(postId, pageable);
        List<CommentEntity> commentEntities = commentPage.getContent();
        return commentEntities.isEmpty() ? Optional.empty()
                : Optional.of(commentEntities.stream()
                .map(commentMapper::toDomain)
                .toList());
    }

    @Override
    public Long countByPostId(Long postId) {
        return commentJpaRepository.countByPostId(postId);
    }

    @Override
    public void saveCommentLike(CommentLike commentLike) {
        CommentLikeEntity commentLikeEntity = commentLikeMapper.toEntity(commentLike);
        commentLikeJpaRepository.save(commentLikeEntity);
    }
}
