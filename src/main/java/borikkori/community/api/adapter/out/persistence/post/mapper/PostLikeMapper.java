package borikkori.community.api.adapter.out.persistence.post.mapper;

import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeIdEntity;
import borikkori.community.api.domain.post.entity.PostLike;
import borikkori.community.api.domain.post.entity.PostLikeId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PostLikeMapper {

    // 엔티티의 복합키(PostLikeId)와 도메인 복합키(PostLikeId)를 매핑하는 커스텀 메서드
    @Named("toDomainPostLikeId")
    default PostLikeId toDomainPostLikeId(PostLikeIdEntity idEntity) {
        if (idEntity == null) return null;
        return new PostLikeId(idEntity.getPostId(), idEntity.getUserId());
    }

    @Named("toEntityPostLikeId")
    default PostLikeIdEntity toEntityPostLikeId(PostLikeId id) {
        if (id == null) return null;
        return new PostLikeIdEntity(id.getPostId(), id.getUserId());
    }

    // 엔티티 -> 도메인
    @Mapping(target = "id", source = "id", qualifiedByName = "toDomainPostLikeId")
    PostLike toDomain(PostLikeEntity entity);

    // 도메인 -> 엔티티
    @Mapping(target = "id", source = "id", qualifiedByName = "toEntityPostLikeId")
    PostLikeEntity toEntity(PostLike postLike);
}
