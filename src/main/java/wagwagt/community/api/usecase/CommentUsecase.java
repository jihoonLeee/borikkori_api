package wagwagt.community.api.usecase;

import wagwagt.community.api.entities.domain.Comment;
import wagwagt.community.api.entities.domain.User;
import wagwagt.community.api.interfaces.controller.dto.requests.CommentWriteRequest;
import wagwagt.community.api.interfaces.controller.dto.responses.CommentListResponse;
import wagwagt.community.api.interfaces.controller.dto.responses.CommentResponse;

public interface CommentUsecase {
    Long write(CommentWriteRequest req);

    Comment findOne(Long commentId);

    CommentListResponse getCommentList(Long postId, int page, int size);

    CommentResponse commentLike(Comment comment , User user);
}
