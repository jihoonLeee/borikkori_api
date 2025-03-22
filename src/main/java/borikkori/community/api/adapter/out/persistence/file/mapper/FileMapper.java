package borikkori.community.api.adapter.out.persistence.file.mapper;

import borikkori.community.api.adapter.out.persistence.file.entity.FileEntity;
import borikkori.community.api.domain.file.entity.File;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {
    File toDomain(FileEntity entity);
    FileEntity toEntity(File domain);
}