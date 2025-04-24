package borikkori.community.api.adapter.in.web.post.response;

import java.util.List;

import borikkori.community.api.adapter.in.web.common.dto.PageResponse;

public class PostListResponse extends PageResponse<PostResponse> {
	public PostListResponse(List<PostResponse> posts, long totalCount, int currentPage, int pageSize, int totalPages) {
		super(posts, totalCount, currentPage, pageSize, totalPages);
	}
}
