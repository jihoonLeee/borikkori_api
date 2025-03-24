package borikkori.community.api.application.domain.file.usecase;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import borikkori.community.api.adapter.in.web.file.request.FileUploadRequest;
import borikkori.community.api.common.enums.FileType;
import borikkori.community.api.domain.file.repository.FileRepository;
import borikkori.community.api.domain.file.service.FileService;
import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.post.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class FileUseCaseImpl implements FileUseCase {

	private final FileRepository fileRepository;
	private final PostRepository postRepository;
	private final FileService fileService;
	@Value("${file.upload-dir}")
	private String uploadDir;
	@Value("${file.base-dir}")
	private String uploadBaseDir;
	@Value("${file.base-url}")
	private String baseUrl;

	@Getter
	private String fileUrl;

	@PostConstruct
	public void init() {
		fileUrl = baseUrl + uploadDir;
	}

	private static final long MAX_SIZE = 10 * (1024 * 1024); // 총 10MB

	@Override
	@Transactional
	public String upload(FileUploadRequest req) throws IOException {
		// 1. 게시글 조회
		Post post = postRepository.findPostById(req.getPostId());
		MultipartFile multipartFile = req.getFile();
		String originalFilename = multipartFile.getOriginalFilename();

		// 2. 파일 확장자 검사
		String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
		if (!isValidExtension(extension)) {
			throw new IllegalStateException("허용되지 않는 파일 확장자입니다.");
		}

		// 3. 파일 크기 검사
		long fileSize = multipartFile.getSize();
		if (fileSize > MAX_SIZE) {
			throw new IllegalStateException("파일 크기가 너무 큽니다.");
		}

		// 4. MIME 타입 검사 (선택적)
		String mimeType = multipartFile.getContentType();
		if (!isValidMimeType(mimeType)) {
			throw new IllegalStateException("허용되지 않는 파일 타입입니다.");
		}

		// 5. 저장 파일명 및 파일 URL 생성
		String savedFileName = UUID.randomUUID() + extension;
		File destination = new File(uploadBaseDir + uploadDir, savedFileName);
		multipartFile.transferTo(destination);
		fileUrl += savedFileName;

		// 6. 파일 상태 결정 (MIME 타입 기반)
		FileType fileType;
		if (mimeType != null && mimeType.startsWith("image/")) {
			fileType = FileType.IMAGE;
		} else if (mimeType != null && mimeType.startsWith("video/")) {
			fileType = FileType.VIDEO;
		} else {
			fileType = FileType.DOCUMENT;
		}

		// 7. 현재 시간을 regDate/updDate로 사용
		LocalDateTime now = LocalDateTime.now();

		// 8. FileEntity 생성 (Builder 사용 없이 생성자 사용)
		borikkori.community.api.domain.file.entity.File file = fileService.createFile(
			originalFilename,       // originalName
			extension,              // extension
			savedFileName,          // savedName
			fileUrl,                // savedUrl
			fileSize,               // fileSize
			fileType,             // fileType
			mimeType,               // contentType
			null,                   // duration (해당 없으면 null)
			null,                   // resolution (해당 없으면 null)
			null                   // metadata (해당 없으면 null)
		);

		// 9. 파일 저장
		fileRepository.saveFile(file);
		return fileUrl;
	}

	@Override
	@Transactional
	public void cleanupFiles() {
		List<borikkori.community.api.domain.file.entity.File> fileEntities = fileRepository.findUnusedFiles();
		for (borikkori.community.api.domain.file.entity.File file : fileEntities) {
			File destination = new File(uploadBaseDir + uploadDir, file.getSavedName());
			boolean isDeleted = destination.delete();
			if (isDeleted) {
				log.info(file.getSavedName() + " 파일 삭제 성공");
				fileRepository.deleteFile(file.getId());
			} else {
				log.info(file.getSavedName() + " 파일 삭제 실패");
			}
		}
	}

	// 확장자 검사 메소드
	private boolean isValidExtension(String extension) {
		String[] validExtensions = {".jpg", ".png", ".gif", ".mp4"};
		return Arrays.asList(validExtensions).contains(extension.toLowerCase());
	}

	// MIME 타입 검사 메소드
	private boolean isValidMimeType(String mimeType) {
		String[] validMimeTypes = {"image/jpeg", "image/png", "image/gif", "video/mp4"};
		return Arrays.asList(validMimeTypes).contains(mimeType);
	}
}
