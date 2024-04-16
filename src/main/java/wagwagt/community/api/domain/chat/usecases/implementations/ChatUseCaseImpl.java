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
import wagwagt.community.api.domain.chat.interfaces.repositories.ChatRepository;
import wagwagt.community.api.domain.chat.usecases.ChatUseCase;
import wagwagt.community.api.domain.post.interfaces.dto.response.PostResponse;
import wagwagt.community.api.domain.user.entities.User;
import wagwagt.community.api.domain.user.interfaces.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatUseCaseImpl implements ChatUseCase {


    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Override
    public ChatRoomListResponse getChatRoomList(CustomUserDetails customUser) {

        List<ChatRoomType> types = new ArrayList<>();
        MbtiType mbti = null;
        if (customUser == null) {
            types.add(ChatRoomType.GUEST);
        } else {
            types.add(ChatRoomType.ALL_GROUP);
            if (customUser.getUser().getMbti() != null) {
                types.add(ChatRoomType.MBTI_GROUP);
                mbti = customUser.getUser().getMbti().getResult();
            }
            System.out.println(mbti + " 엠비");
        }
        ChatRoomRequest req = ChatRoomRequest.builder().mbtiType(mbti).chatRoomTypes(types).build();

        List<ChatRoomResponse> chatRooms = chatRepository.findChatRooms(req).stream()
                .map(chatRoom -> ChatRoomResponse.builder()
                        .name(chatRoom.getName())
                        .type(chatRoom.getType())
                        .build())
                .collect(Collectors.toList());
        return ChatRoomListResponse.builder().rooms(chatRooms).build();
    }

    @Override
    public void saveMessage(MessageRequest req) {
        Optional<User> optionalUser = userRepository.findByEmail(req.getEmail());

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            ChatMessage chatMessage = ChatMessage.builder().message(req.getMessage()).sender(user).chatRoom(new ChatRoom()).build();
            chatRepository.save(chatMessage);
        }
    }
}
