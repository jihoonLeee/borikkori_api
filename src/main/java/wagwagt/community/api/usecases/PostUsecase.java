package wagwagt.community.api.usecases;

import wagwagt.community.api.entities.Post;
import wagwagt.community.api.responses.PostListResponse;
import wagwagt.community.api.responses.PostResponse;

import java.util.List;

public interface PostUsecase {

    Long posting(Post post);

    void modifyPost(Post post);

    void deletePost(Post post);

    PostListResponse getPostList(int page, int size) ;

    PostResponse getPost(Long id);
}
