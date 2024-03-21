package wagwagt.community.api.domain.chat.interfaces.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
}
