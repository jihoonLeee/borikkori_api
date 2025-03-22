package borikkori.community.api.domain.file.entity;

import borikkori.community.api.common.enums.FileStatus;
import borikkori.community.api.common.enums.FileType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class File {
    private final Long id;
    private final String originalName;
    private final String extension;
    private final String savedName;
    private final String savedUrl;
    private final long fileSize;
    private final FileType fileType;
    private FileStatus fileStatus;
    private String contentType;
    private Integer duration; // 동영상의 경우 지속 시간(초), 이미지나 문서는 null
    private String resolution; // 해상도 (예: "1920x1080")
    private String metadata;   // 추가 메타데이터 (JSON 문자열 등)
    private final LocalDateTime regDate;
    private LocalDateTime updDate;

    public void markAsPublished() {
        this.fileStatus = FileStatus.PUBLISHED;
        this.updDate = LocalDateTime.now();
    }

    private void updateModificationDate() {
        this.updDate = LocalDateTime.now();
    }

    public void updateFile(String contentType, Integer duration, String resolution, String metadata, FileStatus newStatus) {
        this.contentType = contentType;
        this.duration = duration;
        this.resolution = resolution;
        this.metadata = metadata;
        this.fileStatus = newStatus;
        updateModificationDate();
    }

}