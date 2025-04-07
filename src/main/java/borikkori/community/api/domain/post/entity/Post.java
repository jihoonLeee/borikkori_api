package borikkori.community.api.domain.post.entity;

import java.time.LocalDateTime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import borikkori.community.api.common.enums.PostStatus;
import borikkori.community.api.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Post {
	private final Long id;
	private final User user;
	private String title;
	private String contents;
	private int visitCount;
	private int likeCount;
	private int disLikeCount;
	private int shareCount;
	private Category category;
	private String thumbnailUrl;
	private PostStatus postStatus;
	private final LocalDateTime regDate;
	private LocalDateTime updDate;

	public void updateContent(String title, String contents) {
		this.title = title;
		this.contents = contents;
		updateModificationDate();
	}

	public void updateCategory(Category category) {
		this.category = category;
		updateModificationDate();
	}

	public void updateThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
		updateModificationDate();
	}

	public void incrementVisit() {
		this.visitCount++;
	}

	public void addLike() {
		this.likeCount++;
	}

	public void addDislike() {
		this.disLikeCount++;
	}

	public void addShare() {
		this.shareCount++;
	}

	public void updatePostStatus(PostStatus status) {
		this.postStatus = status;
		updateModificationDate();
	}

	private void updateModificationDate() {
		this.updDate = LocalDateTime.now();
	}

	public void markAsPublished() {
		this.postStatus = PostStatus.PUBLISHED;
		this.updDate = LocalDateTime.now();
	}

	/**
	 * 게시글의 참여도 점수를 계산합니다.
	 * (방문수 1점, 좋아요 3점, 공유 5점, 싫어요는 마이너스 점수)
	 *
	 * @return 참여도 점수
	 */
	public int calculateEngagementScore() {
		return visitCount + (likeCount * 3) + (shareCount * 5) - (disLikeCount * 2);
	}

	public void updateThumbnailFromContent() {
		if (contents != null && !contents.isEmpty()) {
			Document document = Jsoup.parse(contents);
			Element img = document.selectFirst("img");
			if (img != null) {
				String src = img.attr("src");
				this.thumbnailUrl = src;
				this.updDate = LocalDateTime.now();
			}
		}
	}

}
