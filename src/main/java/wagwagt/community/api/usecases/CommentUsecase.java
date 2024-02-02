package wagwagt.community.api.usecases;

import wagwagt.community.api.entities.Comment;
import wagwagt.community.api.requests.CommentWriteRequest;
import wagwagt.community.api.responses.CommentListResponse;

public interface CommentUsecase {
    Long write(CommentWriteRequest req);

    CommentListResponse getCommentList(Long postId, int page, int size);
}
