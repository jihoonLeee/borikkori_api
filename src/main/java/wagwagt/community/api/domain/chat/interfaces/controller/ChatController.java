package wagwagt.community.api.domain.chat.interfaces.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wagwagt.community.api.common.service.CustomUserDetails;
import wagwagt.community.api.domain.chat.interfaces.dto.request.MessageRequest;
import wagwagt.community.api.domain.chat.interfaces.dto.response.ChatRoomListResponse;
import wagwagt.community.api.domain.chat.usecases.ChatUseCase;

@Tag(name="chat_api", description = "CHAT Apis")
@RequestMapping("chat")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ChatController {

    private final ChatUseCase chatUseCase;

    /*
    * TODO : 채팅방 입장할 때 -> 어떤 채팅방 인지? ->
    *
    * */

    /*
    * 채팅방 목록 보여주기
    * */
    @GetMapping("/rooms")
    public ResponseEntity<ChatRoomListResponse> loadChatRooms(@AuthenticationPrincipal CustomUserDetails customUser){
        ChatRoomListResponse res = chatUseCase.getChatRoomList(customUser);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    /**
     *  TODO :대화중인 채팅 내용 불러오기
     *  최근 대화 100개 불러오고
     *  위로 스크롤 할 때마다 100개씩 더 불러오기 ( 페이지네이션 느낌 )
     *  */
    @GetMapping("/rooms/{roomId}/messages")
    public void loadMessageList(){
        
    }

    /**
     * TODO : 채팅 메시지 저장하기
     * 채팅 입력 할 때마다 호출되는 api 
     * socket입력 시점에 할지 socket 메시지 전송 완료 시점에 할지 정하기
     */
    @PostMapping("/rooms/{chatRoomId}/messages")
    public ResponseEntity<Void> saveMessage(@AuthenticationPrincipal CustomUserDetails customUser,@RequestBody MessageRequest req,@PathVariable Long chatRoomId){
        req.setSender(customUser.getUser());
        chatUseCase.saveMessage(chatRoomId,req);

        return new ResponseEntity<>(HttpStatus.OK);
    }




    /***
     * 모든 사람 채팅방,
     * 같은MBTI 끼리 ( 이 유저의 강아지가 어떤 MBTI인지 알아야함 ) 
     * -> 유저에 MBTI 결과 매핑
     */


    /**
     * TODO : 채팅 메시지 삭제하기
     */
    @DeleteMapping("/messages/{messageId}")
    public void deleteMessage(){

    }
}
