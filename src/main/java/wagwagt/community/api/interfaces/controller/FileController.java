package wagwagt.community.api.interfaces.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wagwagt.community.api.usecase.FileUseCase;
import wagwagt.community.api.usecase.PostUsecase;

import java.io.File;
import java.io.IOException;
import java.net.URI;

@Tag(name="file_api", description = "File Apis")
@RequestMapping("file")
@RequiredArgsConstructor
@RestController
public class FileController {

    private final FileUseCase fileUseCase;

    @Operation(summary = "이미지 업로드", description = "이미지 업로드")
    @PostMapping("/image/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalStateException("업로드된 파일이 비어 있습니다.");
        }
        return ResponseEntity.ok().body(fileUseCase.upload(file));
    }

    // 우선 로컬에 저장 -> S3에 저장

    // 글쓰기 페이지에 들어가면 Post를 바로 생성하고 -> Upload하면 -> Post와 Images 테이블에 저장 (상태 = TEMP)
    // Post : Images = 1 : N 양방향
    // 글 쓰기 들어가면 그 유저 정보로 Post조회 (TEMP)상태가 있으면 불러오거나 삭제(Delete) 선택 , 하루에 한번 TEMP 면 삭제
    // 삭제면 -> Images도 삭제 
    // 등록하면 Post  (상태 = POSTED) 로 변경

}
