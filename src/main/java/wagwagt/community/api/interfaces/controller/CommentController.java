package wagwagt.community.api.interfaces.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wagwagt.community.api.entities.domain.Comment;
import wagwagt.community.api.entities.domain.User;
import wagwagt.community.api.interfaces.controller.dto.requests.CommentWriteRequest;
import wagwagt.community.api.interfaces.controller.dto.responses.CommentListResponse;
import wagwagt.community.api.interfaces.controller.dto.requests.CommentLikeRequest;
import wagwagt.community.api.interfaces.controller.dto.responses.CommentResponse;
import wagwagt.community.api.usecase.CommentUsecase;
import wagwagt.community.api.usecase.PostUsecase;
import wagwagt.community.api.usecase.UserUsecase;

import java.net.URI;
import java.util.Optional;

@Tag(name="comment_api", description = "COMMENT Apis")
@RequestMapping("comment")
@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentUsecase commentUsecase;
    private final UserUsecase userUsecase;
    
    @Operation(summary = "댓글 작성" , description = "댓글 작성")
    @PostMapping
    @Parameter(name = "CommentWriteRequest")
    public ResponseEntity<Void> write(@RequestBody CommentWriteRequest req){
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
    public ResponseEntity<CommentResponse> commentLike(@RequestBody CommentLikeRequest req, @PathVariable Long commentId){
        Comment comment = commentUsecase.findOne(commentId);
        Optional<User> user = userUsecase.findByEmail(req.getEmail());

        CommentResponse res = commentUsecase.commentLike(comment,user.get());
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

}
