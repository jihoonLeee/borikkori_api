package borikkori.community.api.adapter.out.persistence.file.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.common.enums.FileStatus;
import borikkori.community.api.common.enums.FileType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class FileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "post_id")
	private PostEntity postEntity;

	// 원본 파일명
	@Column(nullable = false)
	private String originalName;

	// 파일 확장자
	@Column(nullable = false, length = 10)
	private String extension;

	// 실제 저장된 파일명
	@Column(nullable = false)
	private String savedName;

	// 파일이 저장된 경로 또는 URL
	@Column(nullable = false)
	private String savedUrl;

	// 파일 크기 (바이트 단위)
	@Column(nullable = false)
	private long fileSize;

	// 파일 유형: IMAGE, VIDEO, DOCUMENT 등
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private FileType fileType;

	@Enumerated(EnumType.STRING)
	private FileStatus fileStatus;

	// 콘텐츠 타입 (MIME Type: "image/jpeg", "video/mp4" 등)
	@Column(nullable = false, length = 50)
	private String contentType;

	// 동영상의 경우 지속 시간(초) (이미지나 문서는 null)
	private Integer duration;

	// 이미지나 동영상의 해상도(예: "1920x1080") 또는 기타 정보를 JSON 형식으로 저장
	private String resolution;

	// 추가 메타데이터 (JSON 문자열 등, 필요시 활용)
	@Lob
	private String metadata;

	@CreatedDate
	@Column(updatable = false, nullable = false)
	private LocalDateTime regDate;

	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime updDate;
}
