package borikkori.community.api.adapter.out.persistence.file.mapper;

import org.mapstruct.Mapper;

import borikkori.community.api.adapter.out.persistence.file.entity.FileEntity;
import borikkori.community.api.domain.file.entity.File;

@Mapper(componentModel = "spring")
public interface FileMapper {
	File toDomain(FileEntity entity);

	FileEntity toEntity(File domain);
}
