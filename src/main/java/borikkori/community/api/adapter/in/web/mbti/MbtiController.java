package borikkori.community.api.adapter.in.web.mbti;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import borikkori.community.api.adapter.in.web.mbti.request.MbtiRequest;
import borikkori.community.api.adapter.in.web.mbti.response.MbtiResultResponse;
import borikkori.community.api.application.domain.mbti.usecase.MbtiUseCase;
import borikkori.community.api.config.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "mbti_api", description = "MBTI Apis")
@RequestMapping("mbti")
@RequiredArgsConstructor
@RestController
public class MbtiController {

	private final MbtiUseCase mbtiUseCase;
	private static final int TOP = 3;

	@PostMapping
	public ResponseEntity addMbtiResult(@AuthenticationPrincipal CustomUserDetails customUser,
		@RequestBody MbtiRequest req) {
		if (customUser != null) {
			req.setEmail(customUser.getUser().getEmail());
		}
		mbtiUseCase.addMbtiResult(req);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<MbtiResultResponse>> getTopXResult() {
		return new ResponseEntity<>(mbtiUseCase.getMbtiTopX(TOP), HttpStatus.OK);
	}
}
