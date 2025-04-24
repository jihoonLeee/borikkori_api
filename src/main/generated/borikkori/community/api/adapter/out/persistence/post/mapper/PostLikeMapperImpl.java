package borikkori.community.api.adapter.out.persistence.post.mapper;

import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeEntity;
import borikkori.community.api.common.enums.ReactionType;
import borikkori.community.api.domain.post.entity.PostLike;
import borikkori.community.api.domain.post.entity.PostLikeId;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-17T21:27:33+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class PostLikeMapperImpl implements PostLikeMapper {

    @Override
    public PostLike toDomain(PostLikeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PostLikeId id = null;
        ReactionType reactionType = null;
        LocalDateTime regDate = null;

        id = map( entity.getId() );
        reactionType = entity.getReactionType();
        regDate = entity.getRegDate();

        PostLike postLike = new PostLike( id, reactionType, regDate );

        return postLike;
    }

    @Override
    public PostLikeEntity toEntity(PostLike postLike) {
        if ( postLike == null ) {
            return null;
        }

        PostLikeEntity postLikeEntity = new PostLikeEntity();

        postLikeEntity.setId( map( postLike.getId() ) );

        return postLikeEntity;
    }
}
