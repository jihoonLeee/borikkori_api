package borikkori.community.api.adapter.in.web.post.response;

import lombok.Builder;
import lombok.Getter;
import borikkori.community.api.common.enums.CommentStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class CommentResponse {

    private Long commentId;
    private Long parentCommentId;
    private String nickName;
    private String contents;
    private int likeCnt;
    private CommentStatus status;
    private LocalDateTime regDate;
    private LocalDateTime updDate;
    private List<CommentResponse> children;
}
