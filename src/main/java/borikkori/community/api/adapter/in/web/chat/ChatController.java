package borikkori.community.api.adapter.in.web.chat;

import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.out.persistence.user.mapper.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import borikkori.community.api.config.security.CustomUserDetails;
import borikkori.community.api.adapter.in.web.chat.request.MessageRequest;
import borikkori.community.api.adapter.in.web.chat.response.ChatRoomListResponse;
import borikkori.community.api.application.domain.chat.usecase.ChatUseCase;

@Tag(name="chat_api", description = "CHAT Apis")
@RequestMapping("chat")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ChatController {

    private final ChatUseCase chatUseCase;
   // private final UserMapper userMapper;
    /*
    * TODO : 채팅방 입장할 때 -> 어떤 채팅방 인지? ->
    *
    * */

    /*
    * DONE : 채팅방 목록 보여주기 [2024-04-20] 
    * 유저별 접근 가능한 채팅방 목록 보여줌
    * */
   /* @GetMapping("/rooms")
    public ResponseEntity<ChatRoomListResponse> loadChatRooms(@AuthenticationPrincipal CustomUserDetails customUser){
        ChatRoomListResponse res = chatUseCase.getChatRoomList(customUser);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
*/

    /**
     *  TODO :대화중인 채팅 내용 불러오기
     *  최근 대화 100개 불러오고
     *  위로 스크롤 할 때마다 100개씩 더 불러오기 ( 페이지네이션 느낌 )
     *  */
    @GetMapping("/rooms/{roomId}/messages")
    public void loadMessageList(){
        
    }

    /**
     * 채팅방 입장하기
     * @param customUser 현재 인증된 사용자
     * @param chatRoomId 입장하려는 채팅방 ID
     * @return 채팅방 정보와 상태 코드
     */
//    @GetMapping("/rooms/{chatRoomId}/enter")
//    public ResponseEntity<ChatRoomResponse> enterChatRoom(@AuthenticationPrincipal CustomUserDetails customUser, @PathVariable Long chatRoomId) {
//        try {
//            ChatRoomResponse chatRoomResponse = chatUseCase.enterChatRoom(customUser, chatRoomId);
//            return new ResponseEntity<>(chatRoomResponse, HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("Chat room entering failed", e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    /**
     * DONE : 채팅 메시지 저장하기 [2024-04-20]
     * 채팅 입력 할 때마다 호출되는 api
     * TODO : socket입력 시점에 할지 socket 메시지 전송 완료 시점에 할지 정하기
     */
  /*  @PostMapping("/rooms/{chatRoomId}/messages")
    public ResponseEntity<Void> saveMessage(@AuthenticationPrincipal CustomUserDetails customUser,@RequestBody MessageRequest req,@PathVariable Long chatRoomId){
        UserEntity userEntity = userMapper.toEntity(customUser.getUser());
        req.setSender(userEntity);
        chatUseCase.saveMessage(chatRoomId,req);

        return new ResponseEntity<>(HttpStatus.OK);
    }*/




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
