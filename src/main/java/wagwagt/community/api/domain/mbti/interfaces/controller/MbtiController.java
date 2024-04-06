package wagwagt.community.api.domain.mbti.interfaces.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wagwagt.community.api.common.service.CustomUserDetails;
import wagwagt.community.api.domain.mbti.interfaces.dto.request.MbtiRequest;
import wagwagt.community.api.domain.mbti.interfaces.dto.response.MbtiResultResponse;
import wagwagt.community.api.domain.mbti.usecases.MbtiUseCase;
import wagwagt.community.api.domain.user.entities.User;

import java.util.List;

@Tag(name="mbti_api", description = "MBTI Apis")
@RequestMapping("mbti")
@RequiredArgsConstructor
@RestController
public class MbtiController {

    private final MbtiUseCase mbtiUseCase;
    private static final int TOP = 3;

    @PostMapping
    public ResponseEntity addMbtiResult(@AuthenticationPrincipal CustomUserDetails customUser, @RequestBody MbtiRequest req){
        if(customUser != null){
            req.setEmail(customUser.getUser().getEmail());
        }
        mbtiUseCase.addMbtiResult(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MbtiResultResponse>> getTopXResult(){
        return new ResponseEntity<>(mbtiUseCase.getMbtiTopX(TOP), HttpStatus.OK);
    }
}
