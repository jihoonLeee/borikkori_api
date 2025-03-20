package borikkori.community.api.application.domain.file.usecase;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import borikkori.community.api.adapter.out.persistence.file.entity.FileEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.common.enums.FileType;
import borikkori.community.api.adapter.in.web.file.request.ImageUploadRequest;
import borikkori.community.api.domain.file.repository.FileRepository;
import borikkori.community.api.domain.post.repository.PostRepository;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class FileUseCaseImpl implements FileUseCase {

    private final FileRepository fileRepository;
    private final PostRepository postRepository;
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Value("${file.base-dir}")
    private String uploadBaseDir;
    private String IMAGE_URL;

    @PostConstruct
    public void init() {
        IMAGE_URL = "https://www.bokko.kr/" + uploadDir;
    }
    private static final long MAX_SIZE = 10*(1024*1024); // 총 10MB
    @Override
    @Transactional
    public String upload(ImageUploadRequest req) throws IOException {
        PostEntity postEntity = postRepository.findById(req.getPostId());
        MultipartFile file = req.getFile();
        String originalFilename = file.getOriginalFilename(); // 원본 파일 이름

        // 파일 확장자 검사
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!isValidExtension(extension)) {
            throw new IllegalStateException("허용되지 않는 파일 확장자입니다.");
        }

        // 파일 크기 검사
        long fileSize = file.getSize();
        if (fileSize > MAX_SIZE) {
            throw new IllegalStateException("파일 크기가 너무 큽니다.");
        }

        // MIME 타입 검사 (선택적)
        String mimeType = file.getContentType();
        if (!isValidMimeType(mimeType)) {
            throw new IllegalStateException("허용되지 않는 파일 타입입니다.");
        }

        String savedFileName = UUID.randomUUID().toString() + extension;
        File destination = new File(uploadBaseDir+uploadDir , savedFileName);
        file.transferTo(destination);
        String imageUrl = IMAGE_URL+savedFileName;

        FileEntity fileEntity = FileEntity.builder()
                .originalName(originalFilename)
                .postEntity(postEntity)
                .savedName(savedFileName)
                .extension(extension)
                .savedUrl(imageUrl)
                .imageStatus(FileType.DRAFT)
                .imageSize(fileSize)
                .build();

        fileRepository.save(fileEntity);
        return imageUrl;
    }

    @Override
    @Transactional
    public void cleanupImage() {
        List<FileEntity> imageEntities = fileRepository.findUnusedImages();
        for(FileEntity fileEntity : imageEntities){
            File destination = new File(uploadBaseDir+uploadDir, fileEntity.getSavedName());
            boolean isDeleted = destination.delete();
            if (isDeleted) {
                log.info(fileEntity.getSavedName() + " 파일 삭제 성공");
                fileRepository.delete(fileEntity.getId());
            } else {
                log.info(fileEntity.getSavedName() + " 파일 삭제 실패");
            }
        }
    }


    // 확장자 검사 메소드
    private boolean isValidExtension(String extension) {
        String[] validExtensions = {".jpg", ".png", ".gif"};
        return Arrays.asList(validExtensions).contains(extension.toLowerCase());
    }

    // MIME 타입 검사 메소드 (선택적)
    private boolean isValidMimeType(String mimeType) {
        String[] validMimeTypes = {"image/jpeg", "image/png", "image/gif"};
        return Arrays.asList(validMimeTypes).contains(mimeType);
    }

}
