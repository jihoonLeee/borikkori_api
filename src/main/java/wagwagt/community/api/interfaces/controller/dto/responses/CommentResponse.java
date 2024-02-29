package wagwagt.community.api.interfaces.controller.dto.responses;

import lombok.Builder;
import lombok.Getter;
import wagwagt.community.api.entities.domain.enums.CommentStatus;

import java.time.LocalDateTime;

@Builder
@Getter
public class CommentResponse {

    private Long commentId;
    private String nickName;
    private String contents;
    private int likeCnt;
    private CommentStatus status;
    private LocalDateTime regDate;
    private LocalDateTime updDate;

}
