package borikkori.community.api.application.domain.post.usecase;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import borikkori.community.api.adapter.in.web.post.request.CommentWriteRequest;
import borikkori.community.api.adapter.in.web.post.response.CommentListResponse;
import borikkori.community.api.adapter.in.web.post.response.CommentResponse;
import borikkori.community.api.adapter.out.persistence.post.entity.CommentLikeIdEntity;
import borikkori.community.api.adapter.out.persistence.post.mapper.CommentMapper;
import borikkori.community.api.domain.post.entity.Comment;
import borikkori.community.api.domain.post.entity.CommentLike;
import borikkori.community.api.domain.post.entity.CommentLikeId;
import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.post.repository.CommentRepository;
import borikkori.community.api.domain.post.repository.PostRepository;
import borikkori.community.api.domain.post.service.CommentService;
import borikkori.community.api.domain.post.service.LikeService;
import borikkori.community.api.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentUsecaseImpl implements CommentUsecase {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final LikeService likeService;
	private final CommentService commentService;
	private final CommentMapper commentMapper;

	@Transactional
	@Override
	public Long createComment(CommentWriteRequest req, User user) {
		Post post = postRepository.findPostById(req.getPostId());
		Comment parentComment = Optional.ofNullable(req.getParentCommentId())
			.map(commentRepository::findCommentById)
			.orElse(null);
		Comment comment = commentService.createComment(post, user, req.getContents(), parentComment);
		return commentRepository.saveComment(comment);
	}

	@Override
	public CommentListResponse getCommentList(Long postId, int page, int size) {
		Page<Comment> commentPage = commentRepository.findCommentList(postId, page, size);
		// 매퍼의 toCommentListResponse()를 사용하여 DTO로 변환
		return commentMapper.toCommentListResponse(commentPage);
	}

	@Override
	@Transactional
	public CommentResponse likeComment(Long commentId, Long userId) {
		Comment comment = commentRepository.findCommentById(commentId);
		boolean isEnabled = likeService.isLikeNotExists(CommentLikeIdEntity.class,
			new CommentLikeId(commentId, userId));
		if (isEnabled) {
			commentService.processLike(comment);
			commentRepository.saveComment(comment);
			CommentLike commentLike = new CommentLike(
				new CommentLikeId(comment.getId(), userId), LocalDateTime.now());
			commentRepository.saveCommentLike(commentLike);
			return commentMapper.toResponse(comment);
		} else {
			throw new IllegalStateException("이미 따봉을 눌렀습니다.");
		}
	}
}
