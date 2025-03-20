package borikkori.community.api.adapter.out.persistence.post.mapper;

import borikkori.community.api.adapter.out.persistence.post.entity.CommentEntity;
import borikkori.community.api.domain.post.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
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
}
