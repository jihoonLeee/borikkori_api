package borikkori.community.api.adapter.out.persistence.post.mapper;

import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeEntity;
import borikkori.community.api.domain.post.entity.PostLike;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostLikeMapper {

    // 엔티티 -> 도메인
    PostLike toDomain(PostLikeEntity entity);

    // 도메인 -> 엔티티
    PostLikeEntity toEntity(PostLike postLike);
}
