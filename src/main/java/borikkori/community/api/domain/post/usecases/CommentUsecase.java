package borikkori.community.api.domain.post.usecases;

import borikkori.community.api.domain.post.entities.Comment;
import borikkori.community.api.domain.user.entities.User;
import borikkori.community.api.domain.post.interfaces.dto.request.CommentWriteRequest;
import borikkori.community.api.domain.post.interfaces.dto.response.CommentListResponse;
import borikkori.community.api.domain.post.interfaces.dto.response.CommentResponse;

public interface CommentUsecase {
    Long write(CommentWriteRequest req);

    Comment findOne(Long commentId);

    CommentListResponse getCommentList(Long postId, int page, int size);

    CommentResponse commentLike(Comment comment , User user);
}
