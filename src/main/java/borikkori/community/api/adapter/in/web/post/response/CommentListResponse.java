package borikkori.community.api.adapter.in.web.post.response;

import borikkori.community.api.adapter.in.web.common.dto.PageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class CommentListResponse extends PageResponse<CommentResponse> {
    public CommentListResponse(List<CommentResponse> comments, long totalCount, int currentPage, int pageSize, int totalPages) {
        super(comments, totalCount, currentPage, pageSize, totalPages);
    }
}
