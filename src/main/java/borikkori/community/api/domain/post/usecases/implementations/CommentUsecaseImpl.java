package borikkori.community.api.domain.post.usecases.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.domain.post.services.LikeService;
import borikkori.community.api.domain.post.entities.Comment;
import borikkori.community.api.domain.post.entities.CommentLike;
import borikkori.community.api.domain.post.entities.CommentLikeId;
import borikkori.community.api.domain.post.entities.Post;
import borikkori.community.api.domain.user.entities.User;
import borikkori.community.api.common.enums.CommentStatus;
import borikkori.community.api.domain.post.interfaces.repositories.CommentRepository;
import borikkori.community.api.domain.post.interfaces.repositories.PostRepository;
import borikkori.community.api.domain.user.interfaces.repositories.UserRepository;
import borikkori.community.api.domain.post.interfaces.dto.request.CommentWriteRequest;
import borikkori.community.api.domain.post.interfaces.dto.response.CommentListResponse;
import borikkori.community.api.domain.post.interfaces.dto.response.CommentResponse;
import borikkori.community.api.domain.post.usecases.CommentUsecase;

import java.util.*;
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
    public CommentListResponse getCommentList(Long postId, int page, int size) {
        List<Comment> commentList = commentRepository.findByPostId(postId, page, size)
                .orElse(Collections.emptyList());

        // DTO로 변환 및 ID 기반 맵 구성
        Map<Long, CommentResponse> commentMap = new HashMap<>();
        List<CommentResponse> rootComments = new ArrayList<>();

        for (Comment comment : commentList) {
            CommentResponse dto = CommentResponse.builder()
                    .commentId(comment.getId())
                    .status(comment.getCommentStatus())
                    .parentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null)
                    .nickName(comment.getUser().getName())
                    .contents(comment.getContents())
                    .regDate(comment.getRegDate())
                    .updDate(comment.getUpdDate())
                    .likeCnt(comment.getLikeCnt())
                    .children(new ArrayList<>())  // 초기 빈 리스트 설정
                    .build();
            commentMap.put(dto.getCommentId(), dto);

            // 부모 댓글이 없으면 루트 댓글로 처리
            if (comment.getParentComment() == null) {
                rootComments.add(dto);
            }
        }

        // 각 댓글의 자식 댓글을 부모 DTO에 추가
        for (Comment comment : commentList) {
            if (comment.getParentComment() != null) {
                Long parentId = comment.getParentComment().getId();
                CommentResponse parentDto = commentMap.get(parentId);
                if (parentDto != null) {
                    parentDto.getChildren().add(commentMap.get(comment.getId()));
                }
            }
        }

        return CommentListResponse.builder()
                .comments(rootComments)
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
