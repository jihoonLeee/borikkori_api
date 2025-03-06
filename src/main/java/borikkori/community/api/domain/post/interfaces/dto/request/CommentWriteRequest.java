package borikkori.community.api.domain.post.interfaces.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentWriteRequest {
    private String email;
    private Long postId;
    private Long parentCommentId;
    private String contents;
}
