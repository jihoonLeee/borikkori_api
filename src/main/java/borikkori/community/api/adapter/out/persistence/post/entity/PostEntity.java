package borikkori.community.api.adapter.out.persistence.post.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import borikkori.community.api.adapter.out.persistence.file.entity.FileEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.common.enums.PostStatus;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PostEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	private String title;

	@Column(length = 2000)
	private String contents;

	private int visitCount;

	private int likeCount;

	@OneToMany(mappedBy = "postEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List<FileEntity> fileEntities;

	@OneToMany(mappedBy = "postEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List<CommentEntity> commentEntities;

	@Enumerated(EnumType.STRING)
	private PostStatus postStatus;

	@CreatedDate
	@Column(updatable = false, nullable = false)
	private LocalDateTime regDate;

	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime updDate;

}
