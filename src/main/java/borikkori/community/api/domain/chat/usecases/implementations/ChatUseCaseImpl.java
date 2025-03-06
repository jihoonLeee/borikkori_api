package borikkori.community.api.domain.chat.usecases.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.common.enums.ChatRoomType;
import borikkori.community.api.common.enums.MbtiType;
import borikkori.community.api.infrastructures.security.CustomUserDetails;
import borikkori.community.api.domain.chat.entities.ChatMessage;
import borikkori.community.api.domain.chat.entities.ChatRoom;
import borikkori.community.api.domain.chat.interfaces.dto.request.ChatRoomRequest;
import borikkori.community.api.domain.chat.interfaces.dto.request.MessageRequest;
import borikkori.community.api.domain.chat.interfaces.dto.response.ChatRoomListResponse;
import borikkori.community.api.domain.chat.interfaces.dto.response.ChatRoomResponse;
import borikkori.community.api.domain.chat.interfaces.repositories.ChatMessageRepository;
import borikkori.community.api.domain.chat.interfaces.repositories.ChatRoomRepository;
import borikkori.community.api.domain.chat.usecases.ChatUseCase;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatUseCaseImpl implements ChatUseCase {


    private final ChatRoomRepository roomRepository;
    private final ChatMessageRepository messageRepository;

    @Override
    public ChatRoomListResponse getChatRoomList(CustomUserDetails customUser) {

        ChatRoomType type = null;
        MbtiType mbti = null;
        if (customUser == null) {
            type = ChatRoomType.GUEST;
        } else {
            type = ChatRoomType.ALL;
            if (customUser.getUser().getMbti() != null) {
                mbti = customUser.getUser().getMbti().getResult();
            }
        }
        ChatRoomRequest req = ChatRoomRequest.builder().mbtiType(mbti).chatRoomType(type).build();

        List<ChatRoomResponse> chatRooms = roomRepository.findChatRooms(req).stream()
                .map(chatRoom -> ChatRoomResponse.builder()
                        .name(chatRoom.getName())
                        .chatRoomId(chatRoom.getId())
                        .type(chatRoom.getType())
                        .build())
                .collect(Collectors.toList());
        return ChatRoomListResponse.builder().rooms(chatRooms).build();
    }

    @Override
    @Transactional
    public void saveMessage(Long chatRoomId,MessageRequest req) {
        ChatRoom chatRoom = roomRepository.findById(chatRoomId);
        ChatMessage chatMessage = ChatMessage.builder().message(req.getMessage()).sender(req.getSender()).chatRoom(chatRoom).build();
        messageRepository.save(chatMessage);
    }
}
