package borikkori.community.api.application.domain.file.usecase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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
	@Value("${file.upload-video-dir}")
	private String videoDir;
	@Value("${file.upload-image-dir}")
	private String imageDir;
	@Value("${file.upload-document-dir}")
	private String documentDir;
	@Value("${file.base-dir}")
	private String uploadBaseDir;
	@Value("${file.base-url}")
	private String baseUrl;

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

		// 5. 저장 파일명 생성
		String savedFileName = UUID.randomUUID() + extension;
		String savedUrl;
		FileType fileType;
		// 6. 파일 저장 및 URL 구성 (경로는 Java NIO Paths를 활용)
		if (mimeType != null && mimeType.startsWith("image/")) {
			fileType = FileType.IMAGE;
			File destination = Paths.get(uploadBaseDir, imageDir, savedFileName).toFile();
			multipartFile.transferTo(destination);
			savedUrl = baseUrl + imageDir + savedFileName;
		} else if (mimeType != null && mimeType.startsWith("video/")) {
			fileType = FileType.VIDEO;
			File destination = Paths.get(uploadBaseDir, videoDir, savedFileName).toFile();
			multipartFile.transferTo(destination);
			savedUrl = baseUrl + videoDir + savedFileName;
		} else {
			fileType = FileType.DOCUMENT;
			File destination = Paths.get(uploadBaseDir, documentDir, savedFileName).toFile();
			multipartFile.transferTo(destination);
			savedUrl = baseUrl + documentDir + savedFileName;
		}

		// 7. 현재 시간을 regDate/updDate로 사용
		LocalDateTime now = LocalDateTime.now();

		// 8. FileEntity 생성 (Builder 사용 없이 생성자 사용)
		borikkori.community.api.domain.file.entity.File file = fileService.createFile(
			post,          // post
			originalFilename,   // originalName
			extension,          // extension
			savedFileName,      // savedName
			savedUrl,           // savedUrl
			fileSize,           // fileSize
			fileType,           // fileType
			mimeType,           // contentType
			null,               // duration (없으면 null)
			null,               // resolution (없으면 null)
			null                // metadata (없으면 null)
		);

		// 9. 파일 저장
		fileRepository.saveFile(file);
		return savedUrl;
	}

	@Override
	@Transactional
	public void cleanupFiles() {
		// 사용되지 않는 파일 목록 조회
		List<borikkori.community.api.domain.file.entity.File> fileEntities = fileRepository.findUnusedFiles();
		for (borikkori.community.api.domain.file.entity.File file : fileEntities) {
			String directory;
			if (file.getFileType() == FileType.IMAGE) {
				directory = imageDir;
			} else if (file.getFileType() == FileType.VIDEO) {
				directory = videoDir;
			} else {
				directory = documentDir;
			}
			File targetFile = Paths.get(uploadBaseDir, directory, file.getSavedName()).toFile();
			boolean isDeleted = targetFile.delete();
			if (isDeleted) {
				log.info(file.getSavedName() + " 파일 삭제 성공");
				fileRepository.deleteFile(file.getId());
			} else {
				log.warn(file.getSavedName() + " 파일 삭제 실패");
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
