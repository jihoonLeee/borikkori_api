package borikkori.community.api.domain.post.repository;

import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.post.entity.PostLike;
import borikkori.community.api.domain.user.entity.User;
import org.springframework.data.domain.Page;

public interface PostRepository {


    Long savePost(Post post);
    Page<Post> findPostList(int page , int size);
    long findPostCounts();
    Post findPostById(Long id );
    void savePostLike(PostLike postLike);
    Post findTempByUser(User user);
    void deletePost(Post post);

}
