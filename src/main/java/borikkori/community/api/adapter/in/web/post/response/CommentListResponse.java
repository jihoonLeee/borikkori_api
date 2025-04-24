package borikkori.community.api.adapter.in.web.post.response;

import java.util.List;

import borikkori.community.api.adapter.in.web.common.dto.PageResponse;

public class CommentListResponse extends PageResponse<CommentResponse> {
	public CommentListResponse(List<CommentResponse> comments, long totalCount, int currentPage, int pageSize,
		int totalPages) {
		super(comments, totalCount, currentPage, pageSize, totalPages);
	}
}
