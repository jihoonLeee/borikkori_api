package borikkori.community.api.adapter.out.persistence.file.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import borikkori.community.api.adapter.out.persistence.file.entity.FileEntity;
import borikkori.community.api.adapter.out.persistence.post.mapper.PostMapper;
import borikkori.community.api.domain.file.entity.File;
import borikkori.community.api.domain.user.vo.UserId;

@Mapper(componentModel = "spring", uses = {PostMapper.class})
public interface FileMapper {
	@Mapping(source = "postEntity", target = "post")
	File toDomain(FileEntity entity);

	@Mapping(source = "post", target = "postEntity")
	FileEntity toEntity(File domain);

	default UserId map(Long id) {
		return id == null ? null : new UserId(id);
	}
}
