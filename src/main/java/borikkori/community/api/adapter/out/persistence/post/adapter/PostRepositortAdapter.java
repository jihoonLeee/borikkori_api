package borikkori.community.api.adapter.out.persistence.post.adapter;

import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeEntity;
import borikkori.community.api.adapter.out.persistence.post.mapper.PostLikeMapper;
import borikkori.community.api.adapter.out.persistence.post.mapper.PostMapper;
import borikkori.community.api.adapter.out.persistence.post.repository.SpringDataPostJpaRepository;
import borikkori.community.api.adapter.out.persistence.post.repository.SpringDataPostLikeJpaRepository;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.out.persistence.user.mapper.UserMapper;
import borikkori.community.api.common.enums.PostStatus;
import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.post.entity.PostLike;
import borikkori.community.api.domain.post.repository.PostRepository;
import borikkori.community.api.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositortAdapter implements PostRepository {
    private final SpringDataPostJpaRepository postJpaRepository;
    private final SpringDataPostLikeJpaRepository postLikeJpaRepository;
    private final PostMapper postMapper;
    private final PostLikeMapper postLikeMapper;
    private final UserMapper userMapper;

    @Override
    public Long savePost(Post post) {
        PostEntity postEntity = postMapper.toEntity(post);
        PostEntity saved = postJpaRepository.save(postEntity);
        return saved.getId();
    }

    @Override
    public Page<Post> findPostList(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PostEntity> postEntityPage = postJpaRepository.findAll(pageable);
        return postEntityPage.map(postMapper::toDomain);
    }


    @Override
    public long findPostCounts() {
        return postJpaRepository.count();
    }

    @Override
    public Post findPostById(Long id) {
        PostEntity postEntity = postJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        return postMapper.toDomain(postEntity);
    }

    @Override
    public void savePostLike(PostLike postLike) {
        PostLikeEntity postLikeEntity = postLikeMapper.toEntity(postLike);
        postLikeJpaRepository.save(postLikeEntity);
    }


    @Override
    public Post findTempByUser(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        PostEntity postEntity = postJpaRepository.findLatestTemporaryPost(userEntity, PostStatus.DRAFT)
                .orElseThrow(() -> new RuntimeException("Latest Temporary Post not found"));
        return postMapper.toDomain(postEntity);
    }

    @Override
    public void deletePost(Post post) {
        PostEntity postEntity=  postMapper.toEntity(post);
        postJpaRepository.delete(postEntity);
    }
}
