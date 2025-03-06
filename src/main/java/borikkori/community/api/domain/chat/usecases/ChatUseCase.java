package borikkori.community.api.domain.chat.usecases;

import borikkori.community.api.infrastructures.security.CustomUserDetails;
import borikkori.community.api.domain.chat.interfaces.dto.request.MessageRequest;
import borikkori.community.api.domain.chat.interfaces.dto.response.ChatRoomListResponse;

public interface ChatUseCase {

    ChatRoomListResponse getChatRoomList(CustomUserDetails customUserDetails);
    void saveMessage(Long chatRoomId,MessageRequest req);
}
