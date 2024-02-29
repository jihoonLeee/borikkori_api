package wagwagt.community.api.interfaces.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wagwagt.community.api.entities.domain.Post;
import wagwagt.community.api.entities.domain.User;
import wagwagt.community.api.interfaces.controller.dto.requests.PostLikeRequest;
import wagwagt.community.api.interfaces.controller.dto.requests.PostWriteRequest;
import wagwagt.community.api.interfaces.controller.dto.responses.PostListResponse;
import wagwagt.community.api.interfaces.controller.dto.responses.PostResponse;
import wagwagt.community.api.usecase.PostUsecase;
import wagwagt.community.api.usecase.UserUsecase;

import java.net.URI;
import java.util.Optional;

@Tag(name="post_api", description = "POST Apis")
@RequestMapping("post")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostUsecase postUsecase;
    private final UserUsecase userUsecase;

    @Operation(summary = "글작성" , description = "게시글 작성")
    @Parameter(name = "PostWriteRequest")
    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostWriteRequest req){
        return ResponseEntity.created(URI.create("/register/"+postUsecase.posting(req))).build();
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
    public ResponseEntity<PostResponse> postLike(@RequestBody PostLikeRequest req,@PathVariable Long postId){
        Post post = postUsecase.findOne(postId);
        Optional<User> user = userUsecase.findByEmail(req.getEmail());

        PostResponse res = postUsecase.postLike(post,user.get());
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
