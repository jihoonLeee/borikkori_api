package borikkori.community.api.domain.post.interfaces.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CommentListResponse {
    @Schema(description = "댓글 목록")
    List<CommentResponse> comments;

    Long totalCount;
}
