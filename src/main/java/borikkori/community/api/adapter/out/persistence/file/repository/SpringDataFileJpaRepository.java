package borikkori.community.api.adapter.out.persistence.file.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import borikkori.community.api.adapter.out.persistence.file.entity.FileEntity;
import borikkori.community.api.common.enums.FileStatus;

public interface SpringDataFileJpaRepository extends JpaRepository<FileEntity, Long> {
	@Query("SELECT f FROM FileEntity f WHERE f.postEntity.id = :postId")
	List<FileEntity> findFilesByPostId(@Param("postId") Long postId);

	@Query("SELECT f FROM FileEntity f WHERE f.fileStatus = :fileStatus AND f.regDate < :threshold")
	List<FileEntity> findUnusedFiles(@Param("fileStatus") FileStatus fileStatus,
		@Param("threshold") LocalDateTime threshold);
}
