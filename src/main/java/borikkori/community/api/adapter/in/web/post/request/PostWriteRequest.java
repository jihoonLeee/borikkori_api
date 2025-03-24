package borikkori.community.api.adapter.in.web.post.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostWriteRequest {
	private Long postId;
	private String title;
	private String contents;

	@JsonProperty("isTemp")
	private boolean temp;
}
