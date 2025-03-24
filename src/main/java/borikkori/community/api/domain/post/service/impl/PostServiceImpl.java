package borikkori.community.api.domain.post.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import borikkori.community.api.common.enums.PostStatus;
import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.post.service.PostService;
import borikkori.community.api.domain.user.entity.User;

@Service
public class PostServiceImpl implements PostService {
	@Override
	public Post createPost(User user, String title, String contents) {
		LocalDateTime now = LocalDateTime.now();
		return new Post(null, user, title, contents, 0, 0, PostStatus.DRAFT, now, now);
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
}
