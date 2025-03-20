package borikkori.community.api.application.domain.post.usecase;

import borikkori.community.api.adapter.out.persistence.post.mapper.PostMapper;
import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.post.service.PostService;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.domain.post.service.LikeService;
import borikkori.community.api.adapter.out.persistence.file.entity.FileEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeIdEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.common.enums.FileType;
import borikkori.community.api.common.enums.PostStatus;
import borikkori.community.api.adapter.in.web.post.response.PostTempResponse;
import borikkori.community.api.domain.file.repository.FileRepository;
import borikkori.community.api.domain.post.repository.PostRepository;
import borikkori.community.api.adapter.in.web.post.request.PostWriteRequest;
import borikkori.community.api.adapter.in.web.post.response.PostListResponse;
import borikkori.community.api.adapter.in.web.post.response.PostResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostUsecaseImpl implements PostUsecase {


    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final LikeService likeService;
    private final PostService postService;
    private final PostMapper postMapper;

    @Override
    @Transactional
    public PostResponse  posting(PostWriteRequest req, User user) {


        Post post = postService.createPost(user, req.getTitle(), req.getContents());
        // 2. 도메인 Post 객체를 영속성 엔티티로 변환 후 저장
        Long postId = postRepository.savePost(post);
        // 3. 이미지 상태 업데이트 (인프라 관련 처리)
        activeImage(postId);

        return savedId;
    }


    @Override
    public PostEntity findOne(Long postId){
        return postRepository.findById(postId);
    }

    @Override
    @Transactional
    public void modifyPost(PostEntity postEntity) {

    }

    @Override
    public void deletePost(PostEntity postEntity) {
        postRepository.delete(postEntity);
    }

    @Override
    @Transactional
    public PostResponse getPost(Long id) {
        PostEntity postEntity = postRepository.findById(id);
        postEntity.setVisitCnt(postEntity.getVisitCnt()+1);
        return PostResponse.builder()
                .postId(id)
                .title(postEntity.getTitle())
                .contents(postEntity.getContents())
                .nickName(postEntity.getUser().getName())
                .regDate(postEntity.getRegDate())
                .updDate(postEntity.getUpdDate())
                .visitCnt(postEntity.getVisitCnt())
                .likeCnt(postEntity.getLikeCnt())
                .build();
    }

    @Override
    public PostListResponse getPostList(int page, int size) {

        List<PostResponse> posts= postRepository.findAll(page,size).stream()
                .map(post -> PostResponse.builder()
                        .postId(post.getId())
                        .nickName(post.getUser().getName())
                        .title(post.getTitle())
                        .contents(post.getContents())
                        .regDate(post.getRegDate())
                        .updDate(post.getUpdDate())
                        .likeCnt(post.getLikeCnt())
                        .visitCnt(post.getVisitCnt())
                        .build())
                .collect(Collectors.toList());
        return PostListResponse.builder().posts(posts)
                .totalCount(postRepository.findPostCounts())
                .build();
    }

    @Override
    @Transactional
    public PostResponse postLike(PostEntity postEntity, UserEntity user){
        PostLikeIdEntity postLikeIdEntity = new PostLikeIdEntity(postEntity.getId(), user.getId());
        boolean isEnabled = likeService.likeDupleCheck(PostLikeEntity.class, postLikeIdEntity);

        if(isEnabled){
            postEntity.setLikeCnt(postEntity.getLikeCnt()+1);
            postRepository.save(postEntity);
            postRepository.postLike(PostLikeEntity.builder().postEntity(postEntity).user(user).build());
            return PostResponse.builder()
                    .postId(postEntity.getId())
                    .title(postEntity.getTitle())
                    .contents(postEntity.getContents())
                    .nickName(postEntity.getUser().getName())
                    .regDate(postEntity.getRegDate())
                    .updDate(postEntity.getUpdDate())
                    .visitCnt(postEntity.getVisitCnt())
                    .likeCnt(postEntity.getLikeCnt())
                    .build();
        }else{
            throw new IllegalStateException("이미 따봉을 눌렀습니다.");
        }
    }

    @Override
    @Transactional
    public PostTempResponse postTempCheck(UserEntity user) {
        Optional<PostEntity> optionalPost = postRepository.findTempByUser(user);
        if(optionalPost.isPresent()){
            PostEntity postEntity = optionalPost.get();
            return PostTempResponse.builder()
                    .isTemp(true)
                    .title(postEntity.getTitle())
                    .contents(postEntity.getContents())
                    .postId(postEntity.getId())
                    .build();
        }else{
            PostEntity tempPostEntity = PostEntity.builder().user(user).postStatus(PostStatus.DRAFT).build();
            Long postId = postRepository.save(tempPostEntity);
            System.out.println(postId + " 아이디");
            return PostTempResponse.builder().postId(postId).isTemp(false).build();
        }
    }

    private void activeImage(PostEntity postEntity){
        List<FileEntity> imageEntities = fileRepository.findByPost(postEntity);
        imageEntities.forEach(image -> {
            image.setImageStatus(FileType.PUBLISHED);
            fileRepository.save(image);
        });
    }
}
