package borikkori.community.api.domain.post.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentLike {
    private CommentLikeId id;
    private LocalDateTime regDate;
}
