package borikkori.community.api.adapter.out.persistence.post.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import borikkori.community.api.adapter.in.web.post.response.CommentListResponse;
import borikkori.community.api.adapter.in.web.post.response.CommentResponse;
import borikkori.community.api.adapter.out.persistence.post.entity.CommentEntity;
import borikkori.community.api.domain.post.entity.Comment;

@Mapper(
	componentModel = "spring",
	imports = java.util.Collections.class
)
public interface CommentMapper {

	// 엔티티 -> 도메인
	@Mapping(target = "post", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "parentComment", ignore = true)
	Comment toDomain(CommentEntity entity);

	// 도메인 -> 엔티티
	@Mapping(target = "postEntity", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "parentCommentEntity", ignore = true)
	CommentEntity toEntity(Comment domain);

	@Mapping(target = "commentId", source = "id")
	@Mapping(target = "parentCommentId", expression = "java(comment.getParentComment() != null ? comment.getParentComment().getId() : null)")
	@Mapping(target = "name", source = "user.name")
	@Mapping(target = "contents", source = "contents")
	@Mapping(target = "likeCnt", source = "likeCount")
	@Mapping(target = "status", source = "commentStatus")
	@Mapping(target = "regDate", source = "regDate")
	@Mapping(target = "updDate", source = "updDate")
	@Mapping(target = "children", expression = "java(Collections.emptyList())")
	CommentResponse toResponse(Comment comment);

	List<CommentResponse> toResponseList(List<Comment> comments);

	default CommentListResponse toCommentListResponse(Page<Comment> commentPage) {
		List<CommentResponse> responses = toResponseList(commentPage.getContent());
		return new CommentListResponse(
			responses,
			commentPage.getTotalElements(),
			commentPage.getNumber() + 1,
			commentPage.getSize(),
			commentPage.getTotalPages()
		);
	}
}
