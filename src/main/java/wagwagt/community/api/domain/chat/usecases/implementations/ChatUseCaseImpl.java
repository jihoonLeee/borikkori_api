package wagwagt.community.api.domain.chat.usecases.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wagwagt.community.api.common.enums.ChatRoomType;
import wagwagt.community.api.common.enums.MbtiType;
import wagwagt.community.api.common.service.CustomUserDetails;
import wagwagt.community.api.domain.chat.entities.ChatMessage;
import wagwagt.community.api.domain.chat.entities.ChatRoom;
import wagwagt.community.api.domain.chat.interfaces.dto.request.ChatRoomRequest;
import wagwagt.community.api.domain.chat.interfaces.dto.request.MessageRequest;
import wagwagt.community.api.domain.chat.interfaces.dto.response.ChatRoomListResponse;
import wagwagt.community.api.domain.chat.interfaces.dto.response.ChatRoomResponse;
import wagwagt.community.api.domain.chat.interfaces.repositories.ChatMessageRepository;
import wagwagt.community.api.domain.chat.interfaces.repositories.ChatRoomRepository;
import wagwagt.community.api.domain.chat.usecases.ChatUseCase;

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
