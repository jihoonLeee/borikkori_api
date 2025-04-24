package borikkori.community.api.adapter.in.web.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import borikkori.community.api.adapter.in.web.post.request.PostReactionRequest;
import borikkori.community.api.adapter.in.web.post.request.PostWriteRequest;
import borikkori.community.api.adapter.in.web.post.response.PostListResponse;
import borikkori.community.api.adapter.in.web.post.response.PostNeighborsResponse;
import borikkori.community.api.adapter.in.web.post.response.PostResponse;
import borikkori.community.api.application.domain.post.usecase.PostUsecase;
import borikkori.community.api.config.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "post_api", description = "POST Apis")
@RequestMapping("post")
@RequiredArgsConstructor
@RestController
public class PostController {

	private final PostUsecase postUsecase;

	// 임시 글 작성으로 이미 Post 하나 생성 되어져 있으니까 그거 가지고 업데이트
	@Operation(summary = "글작성", description = "게시글 작성")
	@Parameter(name = "PostWriteRequest")
	@PostMapping
	public ResponseEntity<?> createPost(@AuthenticationPrincipal CustomUserDetails customUser,
		@RequestBody PostWriteRequest req) {
		postUsecase.createPost(req, customUser.getUser());
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@Operation(summary = "글 삭제", description = "게시글 삭제")
	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> deletePost(@AuthenticationPrincipal CustomUserDetails customUser,
		@PathVariable Long postId) {
		// 게시글 소유자 확인 하기..
		postUsecase.deletePost(postId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "글 목록", description = "게시글 목록")
	@GetMapping
	public ResponseEntity<PostListResponse> readPostList(@RequestParam int page) {
		PostListResponse res = postUsecase.getPostList(page, 10);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@Operation(summary = "이웃 게시글 조회", description = "현재 게시글 기준으로 이전글과 다음글을 반환합니다.")
	@GetMapping("/{postId}/neighbors")
	public ResponseEntity<PostNeighborsResponse> getNeighborPosts(@PathVariable Long postId) {
		PostNeighborsResponse response = postUsecase.getNeighborPosts(postId);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "게시글 조회", description = "게시글 목록")
	@GetMapping("/{postId}")
	public ResponseEntity<PostResponse> readPost(@PathVariable Long postId) {
		PostResponse res = postUsecase.getPost(postId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@Operation(summary = "따봉 ~")
	@PostMapping("/reaction")
	public ResponseEntity<PostResponse> reactionPost(
		@AuthenticationPrincipal CustomUserDetails customUser,
		@RequestBody PostReactionRequest req) {
		PostResponse res = postUsecase.reactionPost(req.getPostId(), customUser.getUser().getId().getId(),
			req.getReactionType());
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@Operation(summary = "임시 Post 생성", description = "글쓰기 화면 도입 시")
	@PostMapping("/init")
	public ResponseEntity<PostResponse> postInit(@AuthenticationPrincipal CustomUserDetails customUser,
		@RequestBody PostWriteRequest req) {
		PostResponse res = postUsecase.findOrCreateTempPost(customUser.getUser(), req);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@Operation(summary = "최신 글", description = "최신 게시글 목록")
	@GetMapping("/latest")
	public ResponseEntity<Void> latestPost(@AuthenticationPrincipal CustomUserDetails customUser) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "인기글 ", description = "인기 게시글 목록")
	@GetMapping("/popular")
	public ResponseEntity<Void> popularPost(@AuthenticationPrincipal CustomUserDetails customUser) {

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
