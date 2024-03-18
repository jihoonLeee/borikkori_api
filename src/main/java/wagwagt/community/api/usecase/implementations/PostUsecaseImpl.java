package wagwagt.community.api.usecase.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wagwagt.community.api.common.service.LikeService;
import wagwagt.community.api.entities.domain.*;
import wagwagt.community.api.entities.domain.enums.ImageStatus;
import wagwagt.community.api.entities.domain.enums.PostStatus;
import wagwagt.community.api.interfaces.controller.dto.responses.PostTempResponse;
import wagwagt.community.api.interfaces.controller.repositories.FileRepository;
import wagwagt.community.api.interfaces.controller.repositories.PostRepository;
import wagwagt.community.api.interfaces.controller.repositories.UserRepository;
import wagwagt.community.api.interfaces.controller.dto.requests.PostWriteRequest;
import wagwagt.community.api.interfaces.controller.dto.responses.PostListResponse;
import wagwagt.community.api.interfaces.controller.dto.responses.PostResponse;
import wagwagt.community.api.usecase.PostUsecase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostUsecaseImpl implements PostUsecase {

    private final PostRepository postRepository;
    private final FileRepository fileRepository;
    private final LikeService likeService;

    @Override
    @Transactional
    public Long posting(PostWriteRequest req){
        Post post = postRepository.findById(req.getPostId());
        post.setContents(req.getContents());
        post.setTitle(req.getTitle());
        post.setRegDate(LocalDateTime.now());
        postRepository.save(post);
        activeImage(post); // << 이미지 사용중으로 변경하는 메서드
        return post.getId();
    }

    @Override
    public Post findOne(Long postId){
        return postRepository.findById(postId);
    }

    @Override
    @Transactional
    public void modifyPost(Post post) {

    }

    @Override
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    @Override
    @Transactional
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id);
        post.setVisitCnt(post.getVisitCnt()+1);
        return PostResponse.builder()
                .postId(id)
                .title(post.getTitle())
                .contents(post.getContents())
                .nickName(post.getUser().getName())
                .regDate(post.getRegDate())
                .updDate(post.getUpdDate())
                .visitCnt(post.getVisitCnt())
                .likeCnt(post.getLikeCnt())
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
    public PostResponse postLike(Post post , User user){
        PostLikeId postLikeId = new PostLikeId(post.getId(), user.getId());
        boolean isEnabled = likeService.likeDupleCheck(PostLikeId.class,postLikeId);

        if(isEnabled){
            post.setLikeCnt(post.getLikeCnt()+1);
            postRepository.save(post);
            postRepository.postLike(PostLike.builder().post(post).user(user).build());
            return PostResponse.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .contents(post.getContents())
                    .nickName(post.getUser().getName())
                    .regDate(post.getRegDate())
                    .updDate(post.getUpdDate())
                    .visitCnt(post.getVisitCnt())
                    .likeCnt(post.getLikeCnt())
                    .build();
        }else{
            throw new IllegalStateException("이미 따봉을 눌렀습니다.");
        }
    }

    @Override
    @Transactional
    public PostTempResponse postTempCheck(User user) {
        Optional<Post> optionalPost = postRepository.findTempByUser(user);
        if(optionalPost.isPresent()){
            Post post= optionalPost.get();
            return PostTempResponse.builder().isTemp(true).postId(post.getId()).build();
        }else{
            Post tempPost = Post.builder().user(user).postStatus(PostStatus.DRAFT).build();
            postRepository.save(tempPost);
            return PostTempResponse.builder().isTemp(false).build();
        }
    }

    private void activeImage(Post post){
        List<Image> images = fileRepository.findByPost(post);
        images.forEach(image -> {
            image.setImageStatus(ImageStatus.PUBLISHED);
            fileRepository.save(image);
        });
    }
}
