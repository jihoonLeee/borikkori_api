package borikkori.community.api.adapter.in.web.post.response;

import java.time.LocalDateTime;

import borikkori.community.api.domain.post.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class PostResponse {

	private Long postId;

	private String name;

	private String title;

	private String contents;

	private int visitCnt;

	private int likeCnt;

	private Category category;

	@Setter
	private boolean isTemp = false; // 임시 게시물 용

	private LocalDateTime regDate;

	private LocalDateTime updDate;

}
