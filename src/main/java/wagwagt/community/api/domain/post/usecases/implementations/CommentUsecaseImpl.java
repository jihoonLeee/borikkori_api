package wagwagt.community.api.domain.post.usecases.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wagwagt.community.api.common.service.LikeService;
import wagwagt.community.api.domain.post.entities.Comment;
import wagwagt.community.api.domain.post.entities.CommentLike;
import wagwagt.community.api.domain.post.entities.CommentLikeId;
import wagwagt.community.api.domain.post.entities.Post;
import wagwagt.community.api.domain.user.entities.User;
import wagwagt.community.api.common.enums.CommentStatus;
import wagwagt.community.api.domain.post.interfaces.repositories.CommentRepository;
import wagwagt.community.api.domain.post.interfaces.repositories.PostRepository;
import wagwagt.community.api.domain.user.interfaces.repositories.UserRepository;
import wagwagt.community.api.domain.post.interfaces.dto.request.CommentWriteRequest;
import wagwagt.community.api.domain.post.interfaces.dto.response.CommentListResponse;
import wagwagt.community.api.domain.post.interfaces.dto.response.CommentResponse;
import wagwagt.community.api.domain.post.usecases.CommentUsecase;

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
    private final LikeService likeService;

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

        CommentLikeId commentLikeId = new CommentLikeId(comment.getId(), user.getId());
        boolean isEnabled = likeService.likeDupleCheck(CommentLikeId.class,commentLikeId);

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
