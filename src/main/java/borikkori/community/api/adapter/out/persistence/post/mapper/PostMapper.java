package borikkori.community.api.adapter.out.persistence.post.mapper;

import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.domain.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    // 엔티티 -> 도메인
    Post toDomain(PostEntity entity);

    // 도메인 -> 엔티티
    PostEntity toEntity(Post domain);
}
