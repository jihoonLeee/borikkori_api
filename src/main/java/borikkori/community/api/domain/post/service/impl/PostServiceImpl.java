package borikkori.community.api.domain.post.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import borikkori.community.api.common.enums.PostStatus;
import borikkori.community.api.domain.post.entity.Category;
import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.post.service.CategoryService;
import borikkori.community.api.domain.post.service.PostService;
import borikkori.community.api.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final CategoryService categoryService;

	@Override
	public Post createPost(User user, Category category, String title, String contents) {
		LocalDateTime now = LocalDateTime.now();
		Post post = new Post(null, user, title, contents, 0, 0, 0, 0,
			category, "", PostStatus.DRAFT, now, now);

		post.updateThumbnailFromContent();
		return post;
	}

	@Override
	public void updatePost(Post post, String title, String contents) {
		post.updateContent(title, contents);
	}

	@Override
	public void incrementVisit(Post post) {
		post.incrementVisit();
	}

	@Override
	public void processLike(Post post) {
		post.addLike();
	}

	@Override
	public void processDislike(Post post) {
		post.addDislike();
	}
}
