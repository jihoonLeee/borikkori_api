package borikkori.community.api.application.domain.post.usecase;

import borikkori.community.api.adapter.in.web.post.request.PostWriteRequest;
import borikkori.community.api.adapter.in.web.post.response.PostListResponse;
import borikkori.community.api.adapter.in.web.post.response.PostResponse;
import borikkori.community.api.domain.user.entity.User;

public interface PostUsecase {

    Long createPost(PostWriteRequest req, User user);

    PostListResponse getPostList(int page, int size) ;

    PostResponse getPost(Long id);

    PostResponse likePost(Long postId, Long userId);

    PostResponse findOrCreateTempPost(User user);
}
