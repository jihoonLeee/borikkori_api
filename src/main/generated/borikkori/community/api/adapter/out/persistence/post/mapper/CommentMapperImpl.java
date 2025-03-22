package borikkori.community.api.adapter.out.persistence.post.mapper;

import borikkori.community.api.adapter.in.web.post.response.CommentResponse;
import borikkori.community.api.adapter.out.persistence.post.entity.CommentEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.common.enums.CommentStatus;
import borikkori.community.api.domain.post.entity.Comment;
import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.user.entity.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T17:16:03+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment toDomain(CommentEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String contents = null;
        CommentStatus commentStatus = null;
        LocalDateTime regDate = null;
        LocalDateTime updDate = null;

        id = entity.getId();
        contents = entity.getContents();
        commentStatus = entity.getCommentStatus();
        regDate = entity.getRegDate();
        updDate = entity.getUpdDate();

        Post post = null;
        User user = null;
        Comment parentComment = null;
        int likeCount = 0;

        Comment comment = new Comment( id, post, user, parentComment, contents, commentStatus, likeCount, regDate, updDate );

        return comment;
    }

    @Override
    public CommentEntity toEntity(Comment domain) {
        if ( domain == null ) {
            return null;
        }

        Long id = null;
        String contents = null;
        CommentStatus commentStatus = null;
        LocalDateTime regDate = null;
        LocalDateTime updDate = null;

        id = domain.getId();
        contents = domain.getContents();
        commentStatus = domain.getCommentStatus();
        regDate = domain.getRegDate();
        updDate = domain.getUpdDate();

        PostEntity postEntity = null;
        UserEntity user = null;
        CommentEntity parentCommentEntity = null;
        int likeCnt = 0;

        CommentEntity commentEntity = new CommentEntity( id, postEntity, user, parentCommentEntity, contents, commentStatus, likeCnt, regDate, updDate );

        return commentEntity;
    }

    @Override
    public CommentResponse toResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponse.CommentResponseBuilder commentResponse = CommentResponse.builder();

        commentResponse.commentId( comment.getId() );
        commentResponse.nickName( commentUserName( comment ) );
        commentResponse.contents( comment.getContents() );
        commentResponse.likeCnt( comment.getLikeCount() );
        commentResponse.status( comment.getCommentStatus() );
        commentResponse.regDate( comment.getRegDate() );
        commentResponse.updDate( comment.getUpdDate() );

        commentResponse.parentCommentId( comment.getParentComment() != null ? comment.getParentComment().getId() : null );
        commentResponse.children( Collections.emptyList() );

        return commentResponse.build();
    }

    @Override
    public List<CommentResponse> toResponseList(List<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentResponse> list = new ArrayList<CommentResponse>( comments.size() );
        for ( Comment comment : comments ) {
            list.add( toResponse( comment ) );
        }

        return list;
    }

    private String commentUserName(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        User user = comment.getUser();
        if ( user == null ) {
            return null;
        }
        String name = user.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
