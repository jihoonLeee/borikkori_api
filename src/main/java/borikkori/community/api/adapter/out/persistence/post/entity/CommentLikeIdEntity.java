package borikkori.community.api.adapter.out.persistence.post.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class CommentLikeIdEntity implements Serializable {
    private Long commentId;
    private Long userId;
}
