package wagwagt.community.api.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentWriteRequest {
    private String email;
    private Long postId;
    private Long parentCommentId;
    private String content;
}
