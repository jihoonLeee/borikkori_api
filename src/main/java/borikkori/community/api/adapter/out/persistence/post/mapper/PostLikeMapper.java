package borikkori.community.api.adapter.out.persistence.post.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeIdEntity;
import borikkori.community.api.domain.post.entity.PostLike;
import borikkori.community.api.domain.post.entity.PostLikeId;

@Mapper(componentModel = "spring")
public interface PostLikeMapper {

	// 엔티티 -> 도메인
	PostLike toDomain(PostLikeEntity entity);

	// 도메인 -> 엔티티
	@Mapping(target = "id", source = "id")
	PostLikeEntity toEntity(PostLike postLike);

	default PostLikeIdEntity map(PostLikeId id) {
		if (id == null)
			return null;
		return new PostLikeIdEntity(id.getPostId(), id.getUserId());
	}

	default PostLikeId map(PostLikeIdEntity entity) {
		if (entity == null)
			return null;
		return new PostLikeId(entity.getPostId(), entity.getUserId());
	}
}
