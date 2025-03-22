package borikkori.community.api.adapter.in.web.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class PostResponse {

    private Long postId;

    private String nickName;

    private String title;

    private String contents;

    private int visitCnt;

    private int likeCnt;

    @Setter
    private boolean isTemp = false; // 임시 게시물 용

    private LocalDateTime regDate;

    private LocalDateTime updDate;

}
