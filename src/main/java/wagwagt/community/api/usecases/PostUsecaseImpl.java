package wagwagt.community.api.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wagwagt.community.api.entities.Post;
import wagwagt.community.api.repositories.PostRepository;
import wagwagt.community.api.responses.PostListResponse;
import wagwagt.community.api.responses.PostResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostUsecaseImpl implements PostUsecase{

    private final PostRepository postRepository;

    @Override
    @Transactional
    public Long posting(Post post){
        postRepository.save(post);
        return post.getId();
    }

    @Override
    @Transactional
    public void modifyPost(Post post) {

    }

    @Override
    public void deletePost(Post post) {

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



}
