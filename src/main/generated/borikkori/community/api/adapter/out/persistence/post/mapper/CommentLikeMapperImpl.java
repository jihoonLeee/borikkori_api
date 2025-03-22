package borikkori.community.api.adapter.out.persistence.post.mapper;

import borikkori.community.api.adapter.out.persistence.post.entity.CommentLikeEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.CommentLikeIdEntity;
import borikkori.community.api.domain.post.entity.CommentLike;
import borikkori.community.api.domain.post.entity.CommentLikeId;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T17:16:03+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class CommentLikeMapperImpl implements CommentLikeMapper {

    @Override
    public CommentLike toDomain(CommentLikeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CommentLikeId id = null;
        LocalDateTime regDate = null;

        id = commentLikeIdEntityToCommentLikeId( entity.getId() );
        regDate = entity.getRegDate();

        CommentLike commentLike = new CommentLike( id, regDate );

        return commentLike;
    }

    @Override
    public CommentLikeEntity toEntity(CommentLike domain) {
        if ( domain == null ) {
            return null;
        }

        CommentLikeEntity commentLikeEntity = new CommentLikeEntity();

        return commentLikeEntity;
    }

    protected CommentLikeId commentLikeIdEntityToCommentLikeId(CommentLikeIdEntity commentLikeIdEntity) {
        if ( commentLikeIdEntity == null ) {
            return null;
        }

        Long commentId = null;
        Long userId = null;

        commentId = commentLikeIdEntity.getCommentId();
        userId = commentLikeIdEntity.getUserId();

        CommentLikeId commentLikeId = new CommentLikeId( commentId, userId );

        return commentLikeId;
    }
}
