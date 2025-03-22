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
    public Long saveComment(Comment comment) {
        CommentEntity commentEntity = commentMapper.toEntity(comment);
        CommentEntity saved = commentJpaRepository.save(commentEntity);
        return saved.getId();
    }

    @Override
    public Comment findCommentById(Long id) {
        CommentEntity commentEntity = commentJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
        return commentMapper.toDomain(commentEntity);
    }

    @Override
    public Page<Comment> findCommentList(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CommentEntity> commentPage = commentJpaRepository.findByPostEntity_Id(postId, pageable);
        return commentPage.map(commentMapper::toDomain);
    }


    @Override
    public Long countByPostId(Long postId) {
        return commentJpaRepository.countByPostEntity_Id(postId);
    }

    @Override
    public void saveCommentLike(CommentLike commentLike) {
        CommentLikeEntity commentLikeEntity = commentLikeMapper.toEntity(commentLike);
        commentLikeJpaRepository.save(commentLikeEntity);
    }
}
