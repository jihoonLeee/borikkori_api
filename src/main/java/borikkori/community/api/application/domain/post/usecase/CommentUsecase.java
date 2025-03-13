package borikkori.community.api.application.domain.post.usecase;

import borikkori.community.api.adapter.out.persistence.post.entity.CommentEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.in.web.post.request.CommentWriteRequest;
import borikkori.community.api.adapter.in.web.post.response.CommentListResponse;
import borikkori.community.api.adapter.in.web.post.response.CommentResponse;

public interface CommentUsecase {
    Long write(CommentWriteRequest req);

    CommentEntity findOne(Long commentId);

    CommentListResponse getCommentList(Long postId, int page, int size);

    CommentResponse commentLike(CommentEntity commentEntity, UserEntity user);
}
