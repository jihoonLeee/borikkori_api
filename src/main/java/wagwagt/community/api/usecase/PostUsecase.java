package wagwagt.community.api.usecase;

import wagwagt.community.api.entities.domain.Post;
import wagwagt.community.api.entities.domain.User;
import wagwagt.community.api.interfaces.controller.dto.requests.PostWriteRequest;
import wagwagt.community.api.interfaces.controller.dto.responses.PostListResponse;
import wagwagt.community.api.interfaces.controller.dto.responses.PostResponse;

public interface PostUsecase {

    Long posting(PostWriteRequest req);

    void modifyPost(Post post);

    void deletePost(Post post);
    Post findOne(Long postId);
    PostListResponse getPostList(int page, int size) ;

    PostResponse getPost(Long id);

    PostResponse postLike(Post post , User user);
}
