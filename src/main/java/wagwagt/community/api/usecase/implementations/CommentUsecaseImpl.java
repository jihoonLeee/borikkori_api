package wagwagt.community.api.usecase.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wagwagt.community.api.entities.domain.*;
import wagwagt.community.api.entities.domain.enums.CommentStatus;
import wagwagt.community.api.interfaces.controller.repositories.CommentRepository;
import wagwagt.community.api.interfaces.controller.repositories.PostRepository;
import wagwagt.community.api.interfaces.controller.repositories.UserRepository;
import wagwagt.community.api.interfaces.controller.dto.requests.CommentWriteRequest;
import wagwagt.community.api.interfaces.controller.dto.responses.CommentListResponse;
import wagwagt.community.api.interfaces.controller.dto.responses.CommentResponse;
import wagwagt.community.api.usecase.CommentUsecase;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentUsecaseImpl implements CommentUsecase {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Long write(CommentWriteRequest req){
        Post post = postRepository.findById(req.getPostId());
        User user = userRepository.findByEmail(req.getEmail()).get();
        Comment comment = Comment.builder()
                .post(post)
                .contents(req.getContents())
                .parentComment(null)
                .user(user)
                .commentStatus(CommentStatus.OPEN)
                .build();
        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public Long replyWrite(CommentWriteRequest req){
        Post post = postRepository.findById(req.getPostId());
        User user = userRepository.findByEmail(req.getEmail()).get();
        Comment parentComment = commentRepository.findById(req.getParentCommentId());
        Comment comment = Comment.builder()
                .post(post)
                .contents(req.getContents())
                .parentComment(parentComment)
                .user(user)
                .commentStatus(CommentStatus.OPEN)
                .build();
        commentRepository.save(comment);
        return comment.getId();
    }
    @Override
    public Comment findOne(Long commentId){
        return commentRepository.findById(commentId);
    }

    @Override
    public CommentListResponse getCommentList(Long postId,int page, int size) {
        Optional<List<Comment>> commentOptional = commentRepository.findByPostId(postId,page,size);
        List<CommentResponse> comments = commentOptional.orElse(Collections.emptyList()).stream()
                .map(comment -> CommentResponse.builder()
                        .commentId(comment.getId())
                        .status(comment.getCommentStatus())
                        .nickName(comment.getUser().getName())
                        .contents(comment.getContents())
                        .regDate(comment.getRegDate())
                        .updDate(comment.getUpdDate())
                        .likeCnt(comment.getLikeCnt())
                        .build())
                .collect(Collectors.toList());

        return CommentListResponse.builder().comments(comments)
                .totalCount(commentRepository.findCommentCounts(postId))
                .build();
    }


    @Override
    @Transactional
    public CommentResponse commentLike(Comment comment , User user){
        CommentLikeId commentLikeId = new CommentLikeId(comment.getId(),user.getId());
        boolean isEnabled = commentRepository.likeDupleCheck(commentLikeId);

        if(isEnabled){
            comment.setLikeCnt(comment.getLikeCnt()+1);
            commentRepository.save(comment);
            commentRepository.commentLike(CommentLike.builder().comment(comment).user(user).build());
            return CommentResponse.builder()
                    .commentId(comment.getId())
                    .contents(comment.getContents())
                    .status(comment.getCommentStatus())
                    .likeCnt(comment.getLikeCnt())
                    .nickName(user.getName())
                    .regDate(comment.getRegDate())
                    .updDate(comment.getUpdDate()).build();
        }else{
            throw new IllegalStateException("이미 따봉을 눌렀습니다.");
        }
    }
}
