package borikkori.community.api.adapter.out.persistence.file.mapper;

import borikkori.community.api.adapter.out.persistence.file.entity.FileEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.adapter.out.persistence.post.mapper.PostMapper;
import borikkori.community.api.common.enums.FileStatus;
import borikkori.community.api.common.enums.FileType;
import borikkori.community.api.domain.file.entity.File;
import borikkori.community.api.domain.post.entity.Post;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-04T23:58:52+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class FileMapperImpl implements FileMapper {

    @Autowired
    private PostMapper postMapper;

    @Override
    public File toDomain(FileEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Post post = null;
        Long id = null;
        String originalName = null;
        String extension = null;
        String savedName = null;
        String savedUrl = null;
        long fileSize = 0L;
        FileType fileType = null;
        FileStatus fileStatus = null;
        String contentType = null;
        Integer duration = null;
        String resolution = null;
        String metadata = null;
        LocalDateTime regDate = null;
        LocalDateTime updDate = null;

        post = postMapper.toDomain( entity.getPostEntity() );
        id = entity.getId();
        originalName = entity.getOriginalName();
        extension = entity.getExtension();
        savedName = entity.getSavedName();
        savedUrl = entity.getSavedUrl();
        fileSize = entity.getFileSize();
        fileType = entity.getFileType();
        fileStatus = entity.getFileStatus();
        contentType = entity.getContentType();
        duration = entity.getDuration();
        resolution = entity.getResolution();
        metadata = entity.getMetadata();
        regDate = entity.getRegDate();
        updDate = entity.getUpdDate();

        File file = new File( id, post, originalName, extension, savedName, savedUrl, fileSize, fileType, fileStatus, contentType, duration, resolution, metadata, regDate, updDate );

        return file;
    }

    @Override
    public FileEntity toEntity(File domain) {
        if ( domain == null ) {
            return null;
        }

        PostEntity postEntity = null;
        Long id = null;
        String originalName = null;
        String extension = null;
        String savedName = null;
        String savedUrl = null;
        long fileSize = 0L;
        FileType fileType = null;
        FileStatus fileStatus = null;
        String contentType = null;
        Integer duration = null;
        String resolution = null;
        String metadata = null;
        LocalDateTime regDate = null;
        LocalDateTime updDate = null;

        postEntity = postMapper.toEntity( domain.getPost() );
        id = domain.getId();
        originalName = domain.getOriginalName();
        extension = domain.getExtension();
        savedName = domain.getSavedName();
        savedUrl = domain.getSavedUrl();
        fileSize = domain.getFileSize();
        fileType = domain.getFileType();
        fileStatus = domain.getFileStatus();
        contentType = domain.getContentType();
        duration = domain.getDuration();
        resolution = domain.getResolution();
        metadata = domain.getMetadata();
        regDate = domain.getRegDate();
        updDate = domain.getUpdDate();

        FileEntity fileEntity = new FileEntity( id, postEntity, originalName, extension, savedName, savedUrl, fileSize, fileType, fileStatus, contentType, duration, resolution, metadata, regDate, updDate );

        return fileEntity;
    }
}
