package wagwagt.community.api.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="테스트 api", description = "Swagger 테스트용 API")
@RestController
public class TestController {

    @Operation(summary = "문자열 반복" , description = "파라미터로 받은 문자열 2번 반복")
    @Parameter(name = "str",description = "2번 반복할 문자열")
    @GetMapping("returnString")
    public String returnStr(@RequestParam String str){
        return str + "\n" + str;
    }

    @GetMapping("test")
    public String test(){
        return "테스트 API";
    }

    @Hidden
    @GetMapping("ignore")
    public String ignore(){
        return "무시됨";
    }
}
