package borikkori.community.api.adapter.out.persistence.post.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import borikkori.community.api.adapter.in.web.post.response.PostListResponse;
import borikkori.community.api.adapter.in.web.post.response.PostResponse;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.user.vo.UserId;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface PostMapper {

	// 엔티티 -> 도메인
	Post toDomain(PostEntity entity);

	// 도메인 -> 엔티티
	PostEntity toEntity(Post domain);

	@Mapping(target = "postId", source = "id")
	@Mapping(target = "name", source = "user.name")
	@Mapping(target = "isTemp", ignore = true)
	PostResponse toResponse(Post post);

	List<PostResponse> toResponseList(List<Post> posts);

	// Page<Post>를 PostListResponse로 매핑하는 default 메서드
	default PostListResponse toPostListResponse(Page<Post> postPage) {
		List<PostResponse> responses = toResponseList(postPage.getContent());
		return new PostListResponse(
			responses,
			postPage.getTotalElements(),
			postPage.getNumber() + 1,
			postPage.getSize(),
			postPage.getTotalPages()
		);
	}

	default UserId map(Long value) {
		return value == null ? null : new UserId(value);
	}

	default Long map(UserId userId) {
		return userId == null ? null : userId.getId();
	}

	@Mapping(target = "regDate", ignore = true)
	void updateEntity(Post post, @MappingTarget PostEntity entity);

}
