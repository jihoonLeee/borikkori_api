package borikkori.community.api.adapter.in.web.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageResponse<T> {
	private List<T> data;
	private long totalCount;
	private int currentPage;
	private int pageSize;
	private int totalPages;
}
