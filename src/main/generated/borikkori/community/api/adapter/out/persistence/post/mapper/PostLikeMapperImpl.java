package borikkori.community.api.adapter.out.persistence.post.mapper;

import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeIdEntity;
import borikkori.community.api.domain.post.entity.PostLike;
import borikkori.community.api.domain.post.entity.PostLikeId;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T17:16:03+0900",
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
        LocalDateTime regDate = null;

        id = postLikeIdEntityToPostLikeId( entity.getId() );
        regDate = entity.getRegDate();

        PostLike postLike = new PostLike( id, regDate );

        return postLike;
    }

    @Override
    public PostLikeEntity toEntity(PostLike postLike) {
        if ( postLike == null ) {
            return null;
        }

        PostLikeEntity postLikeEntity = new PostLikeEntity();

        return postLikeEntity;
    }

    protected PostLikeId postLikeIdEntityToPostLikeId(PostLikeIdEntity postLikeIdEntity) {
        if ( postLikeIdEntity == null ) {
            return null;
        }

        Long postId = null;
        Long userId = null;

        postId = postLikeIdEntity.getPostId();
        userId = postLikeIdEntity.getUserId();

        PostLikeId postLikeId = new PostLikeId( postId, userId );

        return postLikeId;
    }
}
