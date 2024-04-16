package wagwagt.community.api.domain.chat.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wagwagt.community.api.common.enums.MessageType;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    private MessageType messageType;
    private Long chatRoomId;
    private String sender;
    private String message;

    public void setSender(String sender) {
        this.sender = sender;
    }
}
