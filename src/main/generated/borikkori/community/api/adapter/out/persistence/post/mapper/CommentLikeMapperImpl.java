package borikkori.community.api.adapter.out.persistence.post.mapper;

import borikkori.community.api.adapter.out.persistence.post.entity.CommentLikeEntity;
import borikkori.community.api.common.enums.ReactionType;
import borikkori.community.api.domain.post.entity.CommentLike;
import borikkori.community.api.domain.post.entity.CommentLikeId;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-04T23:58:52+0900",
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
        ReactionType reactionType = null;
        LocalDateTime regDate = null;

        id = map( entity.getId() );
        reactionType = entity.getReactionType();
        regDate = entity.getRegDate();

        CommentLike commentLike = new CommentLike( id, reactionType, regDate );

        return commentLike;
    }

    @Override
    public CommentLikeEntity toEntity(CommentLike domain) {
        if ( domain == null ) {
            return null;
        }

        CommentLikeEntity commentLikeEntity = new CommentLikeEntity();

        commentLikeEntity.setId( map( domain.getId() ) );

        return commentLikeEntity;
    }
}
