package borikkori.community.api.adapter.in.web.post.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import borikkori.community.api.common.enums.CategoryType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostWriteRequest {
	private Long postId;
	private String title;
	private CategoryType categoryType;
	private CategoryType subCategoryType;
	private String contents;

	@JsonProperty("isTemp")
	private boolean temp;
}
