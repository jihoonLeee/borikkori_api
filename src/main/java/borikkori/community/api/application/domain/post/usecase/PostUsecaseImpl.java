package borikkori.community.api.application.domain.post.usecase;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import borikkori.community.api.adapter.in.web.post.request.PostWriteRequest;
import borikkori.community.api.adapter.in.web.post.response.PostListResponse;
import borikkori.community.api.adapter.in.web.post.response.PostNeighborsResponse;
import borikkori.community.api.adapter.in.web.post.response.PostResponse;
import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeIdEntity;
import borikkori.community.api.adapter.out.persistence.post.mapper.PostMapper;
import borikkori.community.api.domain.file.service.FileService;
import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.post.entity.PostLike;
import borikkori.community.api.domain.post.entity.PostLikeId;
import borikkori.community.api.domain.post.repository.PostRepository;
import borikkori.community.api.domain.post.service.LikeService;
import borikkori.community.api.domain.post.service.PostService;
import borikkori.community.api.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostUsecaseImpl implements PostUsecase {

	private final PostRepository postRepository;
	private final FileService fileService;
	private final PostService postService;
	private final LikeService likeService;
	private final PostMapper postMapper;

	@Override
	@Transactional
	public void createPost(PostWriteRequest req, User user) {
		Post post;
		if (req.getPostId() == null) {
			post = postService.createPost(user, req.getTitle(), req.getContents());
		} else {
			// 업데이트임
			post = postRepository.findPostById(req.getPostId());
			post.updateContent(req.getTitle(), req.getContents());
		}
		if (!req.isTemp()) {
			post.markAsPublished();
		}
		fileService.activateFilesForPost(post);
		postRepository.savePost(post);
	}

	@Override
	@Transactional
	public PostResponse getPost(Long id) {
		Post post = postRepository.findPostById(id);
		postService.incrementVisit(post);
		postRepository.savePost(post);
		return postMapper.toResponse(post);
	}

	@Override
	public PostNeighborsResponse getNeighborPosts(Long postId) {
		Post currentPost = postRepository.findPostById(postId);
		LocalDateTime currentRegDate = currentPost.getRegDate();
		// 이전글 조회 (현재 글보다 등록일이 더 큰 글)
		Long prevPostId = postRepository.findPrevPostId(currentRegDate);
		// 다음글 조회 (현재 글보다 등록일이 더 작은 글)
		Long nextPostId = postRepository.findNextPostId(currentRegDate);

		return new PostNeighborsResponse(prevPostId, nextPostId);
	}

	@Override
	public PostListResponse getPostList(int page, int size) {
		Page<Post> postPage = postRepository.findPostList(page, size);
		return postMapper.toPostListResponse(postPage);
	}

	@Override
	@Transactional
	public PostResponse likePost(Long postId, Long userId) {
		Post post = postRepository.findPostById(postId);

		boolean isEnabled = likeService.isLikeNotExists(PostLikeEntity.class, new PostLikeIdEntity(postId, userId));

		if (isEnabled) {
			postService.processLike(post);
			postRepository.savePost(post);
			PostLike postLike = new PostLike(new PostLikeId(post.getId(), userId), LocalDateTime.now());
			postRepository.savePostLike(postLike);
			return postMapper.toResponse(post);
		} else {
			throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
		}
	}

	@Override
	@Transactional
	public PostResponse findOrCreateTempPost(User user) {
		Optional<Post> optionalPost = postRepository.findTempByUser(user);
		if (optionalPost.isPresent()) {
			// 쓰고있는 글이 있으면 쓰던 글 반환
			PostResponse postResponse = postMapper.toResponse(optionalPost.get());
			postResponse.setTemp(true);
			return postResponse;
		} else {
			// 임시 글이 없으면 새로운 임시 글 생성
			Post tempPost = postService.createPost(user, null, null);
			Post savedPost = postRepository.savePost(tempPost);
			return postMapper.toResponse(savedPost);
		}
	}

	@Transactional
	@Override
	public void deletePost(Long postId) {
		Post post = postRepository.findPostById(postId);
		postRepository.deletePost(post);
	}

}
