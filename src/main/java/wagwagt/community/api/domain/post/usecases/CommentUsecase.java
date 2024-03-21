package wagwagt.community.api.domain.post.usecases;

import wagwagt.community.api.domain.post.entities.Comment;
import wagwagt.community.api.domain.user.entities.User;
import wagwagt.community.api.domain.post.interfaces.dto.request.CommentWriteRequest;
import wagwagt.community.api.domain.post.interfaces.dto.response.CommentListResponse;
import wagwagt.community.api.domain.post.interfaces.dto.response.CommentResponse;

public interface CommentUsecase {
    Long write(CommentWriteRequest req);

    Comment findOne(Long commentId);

    CommentListResponse getCommentList(Long postId, int page, int size);

    CommentResponse commentLike(Comment comment , User user);
}
