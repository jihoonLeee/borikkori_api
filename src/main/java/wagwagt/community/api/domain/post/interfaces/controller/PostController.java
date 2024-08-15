package wagwagt.community.api.domain.post.interfaces.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wagwagt.community.api.common.service.CustomUserDetails;
import wagwagt.community.api.domain.post.entities.Post;
import wagwagt.community.api.domain.post.interfaces.dto.request.PostWriteRequest;
import wagwagt.community.api.domain.post.interfaces.dto.response.PostListResponse;
import wagwagt.community.api.domain.post.interfaces.dto.response.PostResponse;
import wagwagt.community.api.domain.post.interfaces.dto.response.PostTempResponse;
import wagwagt.community.api.domain.post.usecases.PostUsecase;
import wagwagt.community.api.domain.user.entities.User;
import wagwagt.community.api.domain.user.usecases.UserUsecase;

@Tag(name="post_api", description = "POST Apis")
@RequestMapping("post")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostUsecase postUsecase;
    private final UserUsecase userUsecase;


    // 임시 글 작성으로 이미 Post 하나 생성 되어져 있으니까 그거 가지고 업데이트
    @Operation(summary = "글작성" , description = "게시글 작성")
    @Parameter(name = "PostWriteRequest")
    @PostMapping
    public ResponseEntity<?> createPost(@AuthenticationPrincipal CustomUserDetails customUser,@RequestBody PostWriteRequest req){
        System.out.println(req.getPostId() + " 아이디");
        req.setUser(customUser.getUser());
        postUsecase.posting(req);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "글 목록" , description = "게시글 목록")
    @GetMapping
    public ResponseEntity<PostListResponse> getPosts(@RequestParam int page){
        PostListResponse res = postUsecase.getPostList(page,10);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "게시글 조회" , description = "게시글 목록")
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId){
        PostResponse res = postUsecase.getPost(postId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "따봉")
    @PostMapping("/{postId}/like")
    public ResponseEntity<PostResponse> postLike(@AuthenticationPrincipal CustomUserDetails customUser, @PathVariable Long postId){
        Post post = postUsecase.findOne(postId);
        User user = userUsecase.findOne(customUser.getUser().getId()).orElseThrow(() -> new RuntimeException("User not found"));

        PostResponse res = postUsecase.postLike(post, user);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }



    @Operation(summary = "임시 글 체크" , description = "임시 게시글 체크")
    @PostMapping("/init")
    public ResponseEntity<PostTempResponse> tempPost(@AuthenticationPrincipal CustomUserDetails customUser){
        PostTempResponse res =  postUsecase.postTempCheck(customUser.getUser());
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
