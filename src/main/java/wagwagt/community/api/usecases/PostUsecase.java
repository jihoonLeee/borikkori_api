package wagwagt.community.api.usecases;

import wagwagt.community.api.entities.Post;
import wagwagt.community.api.entities.User;
import wagwagt.community.api.requests.PostWriteRequest;
import wagwagt.community.api.responses.PostListResponse;
import wagwagt.community.api.responses.PostResponse;

import java.util.List;

public interface PostUsecase {

    Long posting(PostWriteRequest req);

    void modifyPost(Post post);

    void deletePost(Post post);
    Post findOne(Long postId);
    PostListResponse getPostList(int page, int size) ;

    PostResponse getPost(Long id);

    PostResponse postLike(Post post , User user);
}
