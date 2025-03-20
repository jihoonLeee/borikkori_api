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

    // 커스텀 매핑: 영속성 복합 키(CommentLikeIdEntity) -> 도메인 복합 키(CommentLikeId)
    @Named("toDomainCommentLikeId")
    default CommentLikeId toDomainCommentLikeId(CommentLikeIdEntity idEntity) {
        if (idEntity == null) return null;
        return new CommentLikeId(idEntity.getCommentId(), idEntity.getUserId());
    }

    // 커스텀 매핑: 도메인 복합 키(CommentLikeId) -> 영속성 복합 키(CommentLikeIdEntity)
    @Named("toEntityCommentLikeId")
    default CommentLikeIdEntity toEntityCommentLikeId(CommentLikeId id) {
        if (id == null) return null;
        return new CommentLikeIdEntity(id.getCommentId(), id.getUserId());
    }

    // 엔티티 -> 도메인
    @Mapping(target = "id", source = "id", qualifiedByName = "toDomainCommentLikeId")
    CommentLike toDomain(CommentLikeEntity entity);

    // 도메인 -> 엔티티
    @Mapping(target = "id", source = "id", qualifiedByName = "toEntityCommentLikeId")
    CommentLikeEntity toEntity(CommentLike domain);
}
