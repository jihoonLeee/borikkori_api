package wagwagt.community.api.interfaces.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wagwagt.community.api.common.service.CustomUserDetails;
import wagwagt.community.api.interfaces.controller.dto.requests.MbtiRequest;
import wagwagt.community.api.interfaces.controller.dto.responses.MbtiResultResponse;
import wagwagt.community.api.usecase.MbtiUseCase;

import java.util.List;

@Tag(name="mbti_api", description = "MBTI Apis")
@RequestMapping("mbti")
@RequiredArgsConstructor
@RestController
public class MbtiController {

    private final MbtiUseCase mbtiUseCase;
    private static final int TOPTHREE = 3;

    @PostMapping
    public ResponseEntity addMbtiResult(@AuthenticationPrincipal CustomUserDetails customUser, @RequestBody MbtiRequest req){
        req.setEmail(customUser.getUser().getEmail());
        mbtiUseCase.addMbtiResult(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MbtiResultResponse>> getTopXResult(){
        return new ResponseEntity<>(mbtiUseCase.getMbtiTopX(TOPTHREE), HttpStatus.OK);
    }
}
