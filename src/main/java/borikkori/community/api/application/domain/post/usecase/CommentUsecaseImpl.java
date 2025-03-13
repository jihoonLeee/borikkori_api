package borikkori.community.api.application.domain.post.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.domain.post.service.LikeService;
import borikkori.community.api.adapter.out.persistence.post.entity.CommentEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.CommentLikeEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.CommentLikeIdEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.common.enums.CommentStatus;
import borikkori.community.api.domain.post.repository.CommentRepository;
import borikkori.community.api.domain.post.repository.PostRepository;
import borikkori.community.api.domain.user.repository.UserRepository;
import borikkori.community.api.adapter.in.web.post.request.CommentWriteRequest;
import borikkori.community.api.adapter.in.web.post.response.CommentListResponse;
import borikkori.community.api.adapter.in.web.post.response.CommentResponse;

import java.util.*;

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
        PostEntity postEntity = postRepository.findById(req.getPostId());
        UserEntity user = userRepository.findByEmail(req.getEmail()).get();
        CommentEntity commentEntity = CommentEntity.builder()
                .postEntity(postEntity)
                .contents(req.getContents())
                .parentCommentEntity(null)
                .user(user)
                .commentStatus(CommentStatus.OPEN)
                .build();
        commentRepository.save(commentEntity);
        return commentEntity.getId();
    }

    @Transactional
    public Long replyWrite(CommentWriteRequest req){
        PostEntity postEntity = postRepository.findById(req.getPostId());
        UserEntity user = userRepository.findByEmail(req.getEmail()).get();
        CommentEntity parentCommentEntity = commentRepository.findById(req.getParentCommentId());
        CommentEntity commentEntity = CommentEntity.builder()
                .postEntity(postEntity)
                .contents(req.getContents())
                .parentCommentEntity(parentCommentEntity)
                .user(user)
                .commentStatus(CommentStatus.OPEN)
                .build();
        commentRepository.save(commentEntity);
        return commentEntity.getId();
    }
    @Override
    public CommentEntity findOne(Long commentId){
        return commentRepository.findById(commentId);
    }

    @Override
    public CommentListResponse getCommentList(Long postId, int page, int size) {
        List<CommentEntity> commentEntityList = commentRepository.findByPostId(postId, page, size)
                .orElse(Collections.emptyList());

        // DTO로 변환 및 ID 기반 맵 구성
        Map<Long, CommentResponse> commentMap = new HashMap<>();
        List<CommentResponse> rootComments = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntityList) {
            CommentResponse dto = CommentResponse.builder()
                    .commentId(commentEntity.getId())
                    .status(commentEntity.getCommentStatus())
                    .parentCommentId(commentEntity.getParentCommentEntity() != null ? commentEntity.getParentCommentEntity().getId() : null)
                    .nickName(commentEntity.getUser().getName())
                    .contents(commentEntity.getContents())
                    .regDate(commentEntity.getRegDate())
                    .updDate(commentEntity.getUpdDate())
                    .likeCnt(commentEntity.getLikeCnt())
                    .children(new ArrayList<>())  // 초기 빈 리스트 설정
                    .build();
            commentMap.put(dto.getCommentId(), dto);

            // 부모 댓글이 없으면 루트 댓글로 처리
            if (commentEntity.getParentCommentEntity() == null) {
                rootComments.add(dto);
            }
        }

        // 각 댓글의 자식 댓글을 부모 DTO에 추가
        for (CommentEntity commentEntity : commentEntityList) {
            if (commentEntity.getParentCommentEntity() != null) {
                Long parentId = commentEntity.getParentCommentEntity().getId();
                CommentResponse parentDto = commentMap.get(parentId);
                if (parentDto != null) {
                    parentDto.getChildren().add(commentMap.get(commentEntity.getId()));
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
    public CommentResponse commentLike(CommentEntity commentEntity, UserEntity user){

        CommentLikeIdEntity commentLikeIdEntity = new CommentLikeIdEntity(commentEntity.getId(), user.getId());
        boolean isEnabled = likeService.likeDupleCheck(CommentLikeIdEntity.class, commentLikeIdEntity);

        if(isEnabled){
            commentEntity.setLikeCnt(commentEntity.getLikeCnt()+1);
            commentRepository.save(commentEntity);
            commentRepository.commentLike(CommentLikeEntity.builder().commentEntity(commentEntity).user(user).build());
            return CommentResponse.builder()
                    .commentId(commentEntity.getId())
                    .contents(commentEntity.getContents())
                    .status(commentEntity.getCommentStatus())
                    .likeCnt(commentEntity.getLikeCnt())
                    .nickName(user.getName())
                    .regDate(commentEntity.getRegDate())
                    .updDate(commentEntity.getUpdDate()).build();
        }else{
            throw new IllegalStateException("이미 따봉을 눌렀습니다.");
        }
    }
}
