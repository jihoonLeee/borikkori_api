package wagwagt.community.api.domain.post.usecases;

import wagwagt.community.api.domain.post.entities.Post;
import wagwagt.community.api.domain.user.entities.User;
import wagwagt.community.api.domain.post.interfaces.dto.request.PostWriteRequest;
import wagwagt.community.api.domain.post.interfaces.dto.response.PostListResponse;
import wagwagt.community.api.domain.post.interfaces.dto.response.PostResponse;
import wagwagt.community.api.domain.post.interfaces.dto.response.PostTempResponse;

public interface PostUsecase {

    Long posting(PostWriteRequest req);

    void modifyPost(Post post);

    void deletePost(Post post);
    Post findOne(Long postId);
    PostListResponse getPostList(int page, int size) ;

    PostResponse getPost(Long id);

    PostResponse postLike(Post post , User user);

    PostTempResponse postTempCheck(User user);
}
