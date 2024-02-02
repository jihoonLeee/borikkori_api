package wagwagt.community.api.responses;

import lombok.Builder;
import lombok.Getter;
import wagwagt.community.api.enums.CommentStatus;

import java.time.LocalDateTime;

@Builder
@Getter
public class CommentResponse {

    private Long commentId;
    private String nickName;
    private String content;
    private int likeCnt;
    private CommentStatus status;
    private LocalDateTime regDate;
    private LocalDateTime updDate;

}
