package borikkori.community.api.adapter.in.web.post.response;

import borikkori.community.api.adapter.in.web.common.dto.PageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class PostListResponse extends PageResponse<PostResponse> {
    public PostListResponse(List<PostResponse> posts, long totalCount, int currentPage, int pageSize, int totalPages) {
        super(posts, totalCount, currentPage, pageSize, totalPages);
    }
}
