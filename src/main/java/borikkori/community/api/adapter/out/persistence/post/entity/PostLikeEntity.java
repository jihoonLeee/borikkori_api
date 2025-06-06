package borikkori.community.api.adapter.out.persistence.post.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.common.enums.ReactionType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "post_like")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PostLikeEntity {

	@Setter
	@EmbeddedId
	private PostLikeIdEntity id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", insertable = false, updatable = false)
	private PostEntity postEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private UserEntity user;

	@Column(name = "reaction", nullable = false)
	@Enumerated(EnumType.STRING)
	private ReactionType reactionType;  // LIKE, DISLIKE, NONE

	@CreatedDate
	@Column(updatable = false, nullable = false)
	private LocalDateTime regDate;

}

