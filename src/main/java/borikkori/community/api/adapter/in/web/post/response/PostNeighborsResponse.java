package borikkori.community.api.adapter.in.web.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostNeighborsResponse {
	private Long prevPostId;
	private Long nextPostId;
}
