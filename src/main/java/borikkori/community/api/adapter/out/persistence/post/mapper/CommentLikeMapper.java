package borikkori.community.api.adapter.out.persistence.post.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import borikkori.community.api.adapter.out.persistence.post.entity.CommentLikeEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.CommentLikeIdEntity;
import borikkori.community.api.domain.post.entity.CommentLike;
import borikkori.community.api.domain.post.entity.CommentLikeId;

@Mapper(componentModel = "spring")
public interface CommentLikeMapper {

	// 엔티티 -> 도메인
	CommentLike toDomain(CommentLikeEntity entity);

	// 도메인 -> 엔티티
	@Mapping(target = "id", source = "id")
	CommentLikeEntity toEntity(CommentLike domain);

	default CommentLikeIdEntity map(CommentLikeId id) {
		if (id == null)
			return null;
		return new CommentLikeIdEntity(id.getCommentId(), id.getUserId());
	}

	default CommentLikeId map(CommentLikeIdEntity entity) {
		if (entity == null)
			return null;
		return new CommentLikeId(entity.getCommentId(), entity.getUserId());
	}
}
