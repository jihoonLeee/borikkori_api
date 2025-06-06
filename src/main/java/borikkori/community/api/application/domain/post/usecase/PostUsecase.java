package borikkori.community.api.application.domain.post.usecase;

import borikkori.community.api.adapter.in.web.post.request.PostWriteRequest;
import borikkori.community.api.adapter.in.web.post.response.PostListResponse;
import borikkori.community.api.adapter.in.web.post.response.PostNeighborsResponse;
import borikkori.community.api.adapter.in.web.post.response.PostResponse;
import borikkori.community.api.common.enums.ReactionType;
import borikkori.community.api.domain.user.entity.User;

public interface PostUsecase {

	void createPost(PostWriteRequest req, User user);

	PostListResponse getPostList(int page, int size);

	PostResponse getPost(Long postId);

	PostNeighborsResponse getNeighborPosts(Long postId);

	PostResponse reactionPost(Long postId, Long userId, ReactionType reactionType);

	PostResponse findOrCreateTempPost(User user, PostWriteRequest req);

	void deletePost(Long postId);
}
