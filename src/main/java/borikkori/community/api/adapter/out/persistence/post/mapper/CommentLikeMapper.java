package borikkori.community.api.adapter.out.persistence.post.mapper;

import borikkori.community.api.adapter.out.persistence.post.entity.CommentLikeEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.CommentLikeIdEntity;
import borikkori.community.api.domain.post.entity.CommentLike;
import borikkori.community.api.domain.post.entity.CommentLikeId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CommentLikeMapper {

    // 엔티티 -> 도메인
    CommentLike toDomain(CommentLikeEntity entity);

    // 도메인 -> 엔티티
    CommentLikeEntity toEntity(CommentLike domain);

}
