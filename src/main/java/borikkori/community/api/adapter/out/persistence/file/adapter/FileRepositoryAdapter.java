package borikkori.community.api.adapter.out.persistence.file.adapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import borikkori.community.api.adapter.out.persistence.file.entity.FileEntity;
import borikkori.community.api.adapter.out.persistence.file.mapper.FileMapper;
import borikkori.community.api.adapter.out.persistence.file.repository.SpringDataFileJpaRepository;
import borikkori.community.api.common.enums.FileStatus;
import borikkori.community.api.domain.file.entity.File;
import borikkori.community.api.domain.file.repository.FileRepository;
import borikkori.community.api.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FileRepositoryAdapter implements FileRepository {

	private final SpringDataFileJpaRepository fileJpaRepository;
	private final FileMapper fileMapper;

	@Override
	public void saveFile(File file) {
		FileEntity fileEntity = fileMapper.toEntity(file);
		fileJpaRepository.save(fileEntity);
	}

	@Override
	public File findFileById(Long id) {
		FileEntity fileEntity = fileJpaRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("File not found wit id:" + id));
		return fileMapper.toDomain(fileEntity);
	}

	@Override
	public List<File> findFilesByPost(Post post) {
		List<FileEntity> fileEntities = fileJpaRepository.findFilesByPostId(post.getId());
		return fileEntities.stream()
			.map(fileMapper::toDomain)
			.collect(Collectors.toList());
	}

	@Override
	public List<File> findUnusedFiles() {
		// 사용되지 않는 파일: 예를 들어, 파일이 DRAFT 상태이고 1시간 이전에 등록된 경우
		LocalDateTime threshold = LocalDateTime.now().minusHours(1);
		List<FileEntity> fileEntities = fileJpaRepository.findUnusedFiles(FileStatus.DRAFT, threshold);
		return fileEntities.stream()
			.map(fileMapper::toDomain)
			.collect(Collectors.toList());
	}

	@Override
	public void deleteFile(Long id) {
		fileJpaRepository.deleteById(id);
	}
}
