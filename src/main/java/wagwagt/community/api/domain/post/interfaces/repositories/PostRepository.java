package wagwagt.community.api.domain.post.interfaces.repositories;

import wagwagt.community.api.domain.post.entities.Post;
import wagwagt.community.api.domain.post.entities.PostLike;
import wagwagt.community.api.domain.user.entities.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository {


    Long save(Post post);

    List<Post> findAll(int page , int size);
    long findPostCounts();
    Post findById(Long id );

    void postLike(PostLike postLike);

    Optional<Post> findTempByUser(User user);

    void delete(Post post);
}
