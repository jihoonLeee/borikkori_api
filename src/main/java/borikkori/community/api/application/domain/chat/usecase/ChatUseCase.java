package borikkori.community.api.application.domain.chat.usecase;

import borikkori.community.api.config.security.CustomUserDetails;
import borikkori.community.api.adapter.in.web.chat.request.MessageRequest;
import borikkori.community.api.adapter.in.web.chat.response.ChatRoomListResponse;

public interface ChatUseCase {

    ChatRoomListResponse getChatRoomList(CustomUserDetails customUserDetails);
    void saveMessage(Long chatRoomId,MessageRequest req);
}
