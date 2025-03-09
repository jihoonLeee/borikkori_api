package borikkori.community.api.adapter.in.web.post.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostListResponse {
    @Schema(description = "포스트 목록")
    List<PostResponse> posts;

    Long totalCount;
}
