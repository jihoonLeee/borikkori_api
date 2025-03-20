package borikkori.community.api.domain.post.repository;

import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.post.entity.PostLike;
import borikkori.community.api.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository {


    Long savePost(Post post);

    List<Post> findAllPost(int page , int size);
    long findPostCounts();
    Post findPostById(Long id );

    void savePostLike(PostLike postLike);

    Optional<Post> findTempByUser(User user);

    void deletePost(Post post);
}
