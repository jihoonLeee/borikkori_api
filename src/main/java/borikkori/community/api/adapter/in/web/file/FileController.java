package borikkori.community.api.adapter.in.web.file;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import borikkori.community.api.adapter.in.web.file.request.FileUploadRequest;
import borikkori.community.api.application.domain.file.usecase.FileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "file_api", description = "File Apis")
@RequestMapping("file")
@RequiredArgsConstructor
@RestController
public class FileController {

	private final FileUseCase fileUseCase;

	/*
	 * TODO [이지훈] 추후 S3에 저장 로직으로 변경하기
	 * */
	@Operation(summary = "이미지 업로드", description = "이미지 업로드")
	@PostMapping(path = "/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	// MultipartFile 을 받는다고 명시해주면 좋음
	public ResponseEntity<String> upload(@RequestParam("postId") Long postId,
		@RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			throw new IllegalStateException("업로드된 파일이 비어 있습니다.");
		}
		FileUploadRequest req = new FileUploadRequest();
		req.setFile(file);
		req.setPostId(postId);
		return ResponseEntity.ok().body(fileUseCase.upload(req));
	}

}
