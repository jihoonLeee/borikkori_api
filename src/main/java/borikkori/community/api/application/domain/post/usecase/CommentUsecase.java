package borikkori.community.api.application.domain.post.usecase;

import borikkori.community.api.adapter.in.web.post.request.CommentWriteRequest;
import borikkori.community.api.adapter.in.web.post.response.CommentListResponse;
import borikkori.community.api.adapter.in.web.post.response.CommentResponse;
import borikkori.community.api.common.enums.ReactionType;
import borikkori.community.api.domain.user.entity.User;

public interface CommentUsecase {
	Long createComment(CommentWriteRequest req, User user);

	CommentListResponse getCommentList(Long postId, int page, int size);

	CommentResponse reactionComment(Long commentId, Long userId, ReactionType reactionType);
}
