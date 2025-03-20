package borikkori.community.api.domain.post.entity;

import borikkori.community.api.common.enums.CommentStatus;
import borikkori.community.api.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Comment {
    private Long id;
    private Post post;
    private User user;
    private Comment parentComment;  // 상위 댓글 (없을 수도 있음)
    private String contents;
    private CommentStatus commentStatus;
    private int likeCount;
    private LocalDateTime regDate;
    private LocalDateTime updDate;
}
