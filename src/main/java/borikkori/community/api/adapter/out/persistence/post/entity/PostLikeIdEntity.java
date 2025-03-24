package borikkori.community.api.adapter.out.persistence.post.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class PostLikeIdEntity implements Serializable {
	@Column(name = "post_id")
	private Long postId;
	@Column(name = "user_id")
	private Long userId;
}
