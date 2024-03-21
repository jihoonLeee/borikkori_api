package wagwagt.community.api.domain.chat.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wagwagt.community.api.domain.chat.entities.enums.MessageType;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    private MessageType messageType;
    private Long chatRoomId;
    private Long senderId;
    private String message;
}
