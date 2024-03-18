package wagwagt.community.api.interfaces.controller.repositories;

import wagwagt.community.api.entities.domain.Post;
import wagwagt.community.api.entities.domain.PostLike;
import wagwagt.community.api.entities.domain.PostLikeId;
import wagwagt.community.api.entities.domain.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository {


    void save(Post post);

    List<Post> findAll(int page , int size);
    long findPostCounts();
    Post findById(Long id );

    void postLike(PostLike postLike);

    Optional<Post> findTempByUser(User user);

    void delete(Post post);
}
