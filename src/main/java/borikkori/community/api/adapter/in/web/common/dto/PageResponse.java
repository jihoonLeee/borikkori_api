package borikkori.community.api.adapter.in.web.common.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> data;
    private long totalCount;
    private int currentPage;
    private int pageSize;
    private int totalPages;
}