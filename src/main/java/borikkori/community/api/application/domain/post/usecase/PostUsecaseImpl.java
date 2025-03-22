package borikkori.community.api.application.domain.post.usecase;

import borikkori.community.api.adapter.out.persistence.post.mapper.PostMapper;
import borikkori.community.api.adapter.out.persistence.user.mapper.UserMapper;
import borikkori.community.api.domain.file.entity.File;
import borikkori.community.api.domain.file.service.FileService;
import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.post.entity.PostLike;
import borikkori.community.api.domain.post.entity.PostLikeId;
import borikkori.community.api.domain.post.service.PostService;
import borikkori.community.api.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.domain.post.service.LikeService;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeEntity;
import borikkori.community.api.common.enums.PostStatus;
import borikkori.community.api.domain.file.repository.FileRepository;
import borikkori.community.api.domain.post.repository.PostRepository;
import borikkori.community.api.adapter.in.web.post.request.PostWriteRequest;
import borikkori.community.api.adapter.in.web.post.response.PostListResponse;
import borikkori.community.api.adapter.in.web.post.response.PostResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostUsecaseImpl implements PostUsecase {

    private final PostRepository postRepository;
    private final FileService fileService;
    private final PostService postService;
    private final LikeService likeService;
    private final PostMapper postMapper;

    @Override
    @Transactional
    public Long createPost(PostWriteRequest req, User user){
        // 1. 도메인 서비스로 게시글 생성 (새로운 Post 도메인 객체 생성)
        Post post = postService.createPost(user, req.getTitle(), req.getContents());
        // 2. 도메인 객체를 영속성 엔티티로 변환 후 저장
        Long postId = postRepository.savePost(post);
        // 3. 첨부 파일 활성화 처리 (파일 상태를 PUBLISHED로 변경)
        fileService.activateFilesForPost(post);
        return postId;
    }


    @Override
    @Transactional
    public PostResponse getPost(Long id) {
        Post post = postRepository.findPostById(id);
        postService.incrementVisit(post);
        postRepository.savePost(post);
        return postMapper.toResponse(post);
    }

    @Override
    public PostListResponse getPostList(int page, int size) {
        Page<Post> postPage = postRepository.findPostList(page, size);
        return postMapper.toPostListResponse(postPage);
    }


    @Override
    @Transactional
    public PostResponse likePost(Long postId, Long userId){
        Post post = postRepository.findPostById(postId);
        boolean isEnabled = likeService.isLikeNotExists(PostLikeEntity.class, new PostLikeId(postId, userId));
        if (isEnabled) {
            postService.processLike(post);
            postRepository.savePost(post);
            PostLike postLike = new PostLike(new PostLikeId(post.getId(), userId), LocalDateTime.now());
            postRepository.savePostLike(postLike);
            return postMapper.toResponse(post);
        } else {
            throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
        }
    }

    @Override
    @Transactional
    public PostResponse findOrCreateTempPost(User user) {
        Post post = postRepository.findTempByUser(user);
        if(post != null){
            // 쓰고있는 글이 있으면 쓰던 글 반환
            PostResponse postResponse = postMapper.toResponse(post);
            postResponse.setTemp(true);
            return postResponse;
        }else{
            // 쓰고있던 글 없으면 새로운 임시 글 생성
            Post tempPost = postService.createPost(user,null,null);
            postRepository.savePost(tempPost);
            return postMapper.toResponse(tempPost);
        }
    }


}
