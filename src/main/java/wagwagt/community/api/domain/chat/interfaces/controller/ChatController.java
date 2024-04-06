package wagwagt.community.api.domain.chat.interfaces.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wagwagt.community.api.domain.chat.usecases.ChatUseCase;

@Tag(name="chat_api", description = "CHAT Apis")
@RequestMapping("chat")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ChatController {

    private final ChatUseCase chatUseCase;

    @GetMapping("/message")
    public void sendMessage(){
        log.info("챗팅!");
    }
    /**
     *  TODO :대화중인 채팅 내용 불러오기
     *  최근 대화 100개 불러오고
     *  위로 스크롤 할 때마다 100개씩 더 불러오기 ( 페이지네이션 느낌 )
     *  */
    public void loadMessageList(){

    }

    /**
     * TODO : 채팅 메시지 저장하기
     * 채팅 입력 할 때마다 호출되는 api 
     * socket입력 시점에 할지 socket 메시지 전송 완료 시점에 할지 정하기
     */
    public void saveMessage(){

    }




    /***
     * 모든 사람 채팅방,
     * 같은MBTI 끼리 ( 이 유저의 강아지가 어떤 MBTI인지 알아야함 ) 
     * -> 유저에 MBTI 결과 매핑
     */


    /**
     * TODO : 채팅 메시지 삭제하기
     */
    public void deleteMessage(){

    }
}
