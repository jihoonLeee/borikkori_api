package borikkori.community.api.domain.post.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;

import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.post.entity.PostLike;
import borikkori.community.api.domain.user.entity.User;

public interface PostRepository {

	Post savePost(Post post);

	Page<Post> findPostList(int page, int size);

	Long findPrevPostId(LocalDateTime currentRegDate);

	Long findNextPostId(LocalDateTime currentRegDate);

	Post findPostById(Long id);

	void savePostLike(PostLike postLike);

	Optional<Post> findTempByUser(User user);

	void deletePost(Post post);

}
