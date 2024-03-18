package wagwagt.community.api.interfaces.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wagwagt.community.api.common.service.CustomUserDetails;
import wagwagt.community.api.interfaces.controller.dto.requests.UploadRequest;
import wagwagt.community.api.usecase.FileUseCase;

import java.io.IOException;

@Tag(name="file_api", description = "File Apis")
@RequestMapping("file")
@RequiredArgsConstructor
@RestController
public class FileController {

    private final FileUseCase fileUseCase;

    @Operation(summary = "이미지 업로드", description = "이미지 업로드")
    @PostMapping(path = "/image/upload" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // MultipartFile 을 받는다고 명시해주면 좋음
    public ResponseEntity<String> upload(@RequestParam("postId") Long postId, @RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalStateException("업로드된 파일이 비어 있습니다.");
        }
        UploadRequest req = new UploadRequest();
        req.setFile(file);
        req.setPostId(postId);
        return ResponseEntity.ok().body(fileUseCase.upload(req));
    }

    // 우선 로컬에 저장 -> 추후 S3에 저장

}
