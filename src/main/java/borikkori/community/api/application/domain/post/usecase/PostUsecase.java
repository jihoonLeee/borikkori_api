package borikkori.community.api.application.domain.post.usecase;

import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.in.web.post.request.PostWriteRequest;
import borikkori.community.api.adapter.in.web.post.response.PostListResponse;
import borikkori.community.api.adapter.in.web.post.response.PostResponse;
import borikkori.community.api.adapter.in.web.post.response.PostTempResponse;

public interface PostUsecase {

    Long posting(PostWriteRequest req);

    void modifyPost(PostEntity postEntity);

    void deletePost(PostEntity postEntity);
    PostEntity findOne(Long postId);
    PostListResponse getPostList(int page, int size) ;

    PostResponse getPost(Long id);

    PostResponse postLike(PostEntity postEntity, UserEntity user);

    PostTempResponse postTempCheck(UserEntity user);
}
