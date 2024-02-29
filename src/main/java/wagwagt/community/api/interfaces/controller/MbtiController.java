package wagwagt.community.api.interfaces.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity addMbtiResult(@RequestBody MbtiRequest req){
        System.out.println(req.getResult() + "  :  " + req.getEmail() + "굿");
        mbtiUseCase.addMbtiResult(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MbtiResultResponse>> getTopXResult(){
        return new ResponseEntity<>(mbtiUseCase.getMbtiTopX(TOPTHREE), HttpStatus.OK);
    }
}
