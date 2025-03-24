package borikkori.community.api.domain.post.service;

import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.user.entity.User;

public interface PostService {

	/**
	 * 신규 게시글 생성
	 */
	Post createPost(User user, String title, String contents);

	/**
	 * 기존 게시글 수정
	 */
	void updatePost(Post post, String title, String contents);

	/**
	 * 게시글의 방문수 증가
	 */
	void incrementVisit(Post post);

	/**
	 * 게시글에 좋아요
	 */
	void processLike(Post post);
}
