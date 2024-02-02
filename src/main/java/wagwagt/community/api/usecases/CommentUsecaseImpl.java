package wagwagt.community.api.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wagwagt.community.api.entities.Comment;
import wagwagt.community.api.entities.Post;
import wagwagt.community.api.entities.User;
import wagwagt.community.api.enums.CommentStatus;
import wagwagt.community.api.repositories.CommentRepository;
import wagwagt.community.api.repositories.PostRepository;
import wagwagt.community.api.repositories.UserRepository;
import wagwagt.community.api.requests.CommentWriteRequest;
import wagwagt.community.api.responses.CommentListResponse;
import wagwagt.community.api.responses.CommentResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentUsecaseImpl implements CommentUsecase{

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
                .content(req.getContent())
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
                .content(req.getContent())
                .parentComment(parentComment)
                .user(user)
                .commentStatus(CommentStatus.OPEN)
                .build();
        commentRepository.save(comment);
        return comment.getId();
    }


    @Override
    public CommentListResponse getCommentList(Long postId,int page, int size) {
        Optional<List<Comment>> commentOptional = commentRepository.findByPostId(postId,page,size);
        List<CommentResponse> comments = commentOptional.orElse(Collections.emptyList()).stream()
                .map(comment -> CommentResponse.builder()
                        .commentId(comment.getId())
                        .status(comment.getCommentStatus())
                        .nickName(comment.getUser().getName())
                        .content(comment.getContent())
                        .regDate(comment.getRegDate())
                        .updDate(comment.getUpdDate())
                        .likeCnt(comment.getLikeCnt())
                        .build())
                .collect(Collectors.toList());

        return CommentListResponse.builder().comments(comments)
                .totalCount(commentRepository.findCommentCounts(postId))
                .build();
    }
}
