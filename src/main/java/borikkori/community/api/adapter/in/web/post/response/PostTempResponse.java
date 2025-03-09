package borikkori.community.api.adapter.in.web.post.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostTempResponse {

    private boolean isTemp;

    private Long postId;

    private String nickName;

    private String title;

    private String contents;

    private int visitCnt;

    private int likeCnt;

    private LocalDateTime regDate;

    private LocalDateTime updDate;
}
