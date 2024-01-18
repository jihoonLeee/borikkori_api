package wagwagt.community.api.responses;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PostResponse {

    private Long postId;

    private String nickName;

    private String title;

    private String contents;

    private int visitCnt;

    private int likeCnt;

    private LocalDateTime regDate;

    private LocalDateTime updDate;

}
