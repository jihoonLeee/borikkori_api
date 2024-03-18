package wagwagt.community.api.usecase.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wagwagt.community.api.entities.domain.Image;
import wagwagt.community.api.entities.domain.Post;
import wagwagt.community.api.entities.domain.enums.ImageStatus;
import wagwagt.community.api.interfaces.controller.dto.requests.UploadRequest;
import wagwagt.community.api.interfaces.controller.repositories.FileRepository;
import wagwagt.community.api.interfaces.controller.repositories.PostRepository;
import wagwagt.community.api.usecase.FileUseCase;

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

    private final String IMAGE_URL = "http://localhost:8080/images/";
    private static final long MAX_SIZE = 10*(1024*1024); // 총 10MB
    @Override
    @Transactional
    public String upload(UploadRequest req) throws IOException {
        Post post = postRepository.findById(req.getPostId());
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
        File destination = new File("D:/upload/images/" , savedFileName);
        file.transferTo(destination);
        String imageUrl = IMAGE_URL+file.getOriginalFilename();

        Image image = Image.builder()
                .originalName(originalFilename)
                .post(post)
                .savedName(savedFileName)
                .extension(extension)
                .savedUrl(imageUrl)
                .imageStatus(ImageStatus.DRAFT)
                .imageSize(fileSize)
                .build();

        fileRepository.save(image);
        return imageUrl;
    }

    @Override
    @Transactional
    public void cleanupImage() {
        List<Image> images = fileRepository.findUnusedImages();
        for(Image image: images){
            File destination = new File("D:/upload/images/", image.getSavedName());
            boolean isDeleted = destination.delete();
            if (isDeleted) {
                log.info(image.getSavedName() + " 파일 삭제 성공");
                fileRepository.delete(image.getId());
            } else {
                log.info(image.getSavedName() + " 파일 삭제 실패");
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
