package wagwagt.community.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import wagwagt.community.api.entities.Post;
import wagwagt.community.api.entities.User;
import wagwagt.community.api.requests.CommentWriteRequest;
import wagwagt.community.api.responses.CommentListResponse;
import wagwagt.community.api.responses.PostListResponse;
import wagwagt.community.api.usecases.CommentUsecase;
import wagwagt.community.api.usecases.PostUsecase;
import wagwagt.community.api.usecases.UserUsecase;

import java.net.URI;

@Tag(name="comment_api", description = "COMMENT Apis")
@RequestMapping("comments")
@RequiredArgsConstructor
@RestController
public class CommentController {
    private final PostUsecase postUsecase;
    private final CommentUsecase commentUsecase;
    private final UserUsecase userUsecase;
    
    @Operation(summary = "댓글 작성" , description = "댓글 작성")
    @PostMapping("/write")
    @Parameter(name = "CommentWriteRequest")
    public ResponseEntity<Void> write(@RequestBody CommentWriteRequest req){
        return ResponseEntity.created(URI.create("/write/"+commentUsecase.write(req))).build();
    }

    @Operation(summary = "댓글 목록 작성" , description = "댓글 목록")
    @GetMapping("/list")
    public ResponseEntity<CommentListResponse> commentList(@RequestParam Long id , @RequestParam int page){
        CommentListResponse res = commentUsecase.getCommentList(id,page,10);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
