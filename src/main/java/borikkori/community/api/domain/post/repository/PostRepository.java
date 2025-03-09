package borikkori.community.api.domain.post.repository;

import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface PostRepository {


    Long save(PostEntity postEntity);

    List<PostEntity> findAll(int page , int size);
    long findPostCounts();
    PostEntity findById(Long id );

    void postLike(PostLikeEntity postLikeEntity);

    Optional<PostEntity> findTempByUser(UserEntity user);

    void delete(PostEntity postEntity);
}
