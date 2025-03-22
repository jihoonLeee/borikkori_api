package borikkori.community.api.domain.file.service.impl;

import borikkori.community.api.common.enums.FileStatus;
import borikkori.community.api.common.enums.FileType;
import borikkori.community.api.domain.file.entity.File;
import borikkori.community.api.domain.file.repository.FileRepository;
import borikkori.community.api.domain.file.service.FileService;
import borikkori.community.api.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    @Transactional
    public void activateFilesForPost(Post post) {
        List<File> files = fileRepository.findFilesByPost(post);
        for (File file : files) {
            file.markAsPublished();    // 파일 상태를 PUBLISHED로 변경
            fileRepository.saveFile(file);
        }
    }


    @Override
    public File createFile(String originalName,
                                  String extension,
                                  String savedName,
                                  String savedUrl,
                                  long fileSize,
                                  FileType fileType,
                                  String contentType,
                                  Integer duration,
                                  String resolution,
                                  String metadata) {
        LocalDateTime now = LocalDateTime.now();
        return new File(
                null,                    // 아직 ID 미할당 (영속화 시 할당)
                originalName,
                extension,
                savedName,
                savedUrl,
                fileSize,
                fileType,
                FileStatus.DRAFT,        // 초기 상태 DRAFT
                contentType,
                duration,
                resolution,
                metadata,
                now,                     // regDate
                now                      // updDate
        );
    }

    @Override
    public void updateFile(File file, String contentType, Integer duration, String resolution, String metadata, FileStatus newStatus) {
        file.updateFile(contentType,duration,resolution,metadata,newStatus);
    }

}
