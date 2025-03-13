package borikkori.community.api.adapter.in.web.post;

import borikkori.community.api.adapter.out.persistence.user.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import borikkori.community.api.config.security.CustomUserDetails;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.adapter.in.web.post.request.PostWriteRequest;
import borikkori.community.api.adapter.in.web.post.response.PostListResponse;
import borikkori.community.api.adapter.in.web.post.response.PostResponse;
import borikkori.community.api.adapter.in.web.post.response.PostTempResponse;
import borikkori.community.api.application.domain.post.usecase.PostUsecase;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.application.domain.user.usecase.UserRegistrationUsecase;

@Tag(name="post_api", description = "POST Apis")
@RequestMapping("post")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostUsecase postUsecase;
    private final UserRegistrationUsecase userRegistrationUsecase;
    private final UserMapper userMapper;

    // 임시 글 작성으로 이미 Post 하나 생성 되어져 있으니까 그거 가지고 업데이트
    @Operation(summary = "글작성" , description = "게시글 작성")
    @Parameter(name = "PostWriteRequest")
    @PostMapping
    public ResponseEntity<?> createPost(@AuthenticationPrincipal CustomUserDetails customUser,@RequestBody PostWriteRequest req){
        System.out.println(req.getPostId() + " 아이디");
        req.setUser(userMapper.toEntity(customUser.getUser()));
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
        PostEntity postEntity = postUsecase.findOne(postId);
       // UserEntity user = userRegistrationUsecase.find(customUser.getUser().getId()).orElseThrow(() -> new RuntimeException("User not found"));

       // PostResponse res = postUsecase.postLike(postEntity, user);
        // new ResponseEntity<>(res, HttpStatus.OK);
        return null;
    }



    @Operation(summary = "임시 글 체크" , description = "임시 게시글 체크")
    @PostMapping("/init")
    public ResponseEntity<PostTempResponse> tempPost(@AuthenticationPrincipal CustomUserDetails customUser){
       // PostTempResponse res =  postUsecase.postTempCheck(customUser.getUser());
       // return new ResponseEntity<>(res,HttpStatus.OK);
        return null;
    }


    @Operation(summary = "최신 글" , description = "최신 게시글 목록")
    @GetMapping("/latest")
    public ResponseEntity<Void> latestPost(@AuthenticationPrincipal CustomUserDetails customUser){

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "인기글 " , description = "인기 게시글 목록")
    @GetMapping("/popular")
    public ResponseEntity<Void> popularPost(@AuthenticationPrincipal CustomUserDetails customUser){

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
