package borikkori.community.api.domain.post.interfaces.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import borikkori.community.api.infrastructures.security.CustomUserDetails;
import borikkori.community.api.domain.post.entities.Comment;
import borikkori.community.api.domain.post.interfaces.dto.request.CommentWriteRequest;
import borikkori.community.api.domain.post.interfaces.dto.response.CommentListResponse;
import borikkori.community.api.domain.post.interfaces.dto.request.CommentLikeRequest;
import borikkori.community.api.domain.post.interfaces.dto.response.CommentResponse;
import borikkori.community.api.domain.post.usecases.CommentUsecase;

import java.net.URI;

@Tag(name="comment_api", description = "COMMENT Apis")
@RequestMapping("comment")
@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentUsecase commentUsecase;

    @Operation(summary = "댓글 작성" , description = "댓글 작성")
    @PostMapping
    @Parameter(name = "CommentWriteRequest")
    public ResponseEntity<Void> write(@AuthenticationPrincipal CustomUserDetails customUser,@RequestBody CommentWriteRequest req){
        req.setEmail(customUser.getUser().getEmail());
        return ResponseEntity.created(URI.create("/write/"+commentUsecase.write(req))).build();
    }

    @Operation(summary = "댓글 목록" , description = "댓글 목록")
    @GetMapping
    public ResponseEntity<CommentListResponse> commentList(@RequestParam Long id , @RequestParam int page){
        CommentListResponse res = commentUsecase.getCommentList(id,page,10);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @Operation(summary = "따봉")
    @PostMapping("/{commentId}/like")
    public ResponseEntity<CommentResponse> commentLike(@AuthenticationPrincipal CustomUserDetails customUser, @RequestBody CommentLikeRequest req, @PathVariable Long commentId){
        Comment comment = commentUsecase.findOne(commentId);
        CommentResponse res = commentUsecase.commentLike(comment,customUser.getUser());
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
