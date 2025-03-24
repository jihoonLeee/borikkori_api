package borikkori.community.api.application.domain.chat.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import borikkori.community.api.domain.chat.repository.ChatMessageRepository;
import borikkori.community.api.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatUseCaseImpl implements ChatUseCase {

	private final ChatRoomRepository roomRepository;
	private final ChatMessageRepository messageRepository;

    /*@Override
    public ChatRoomListResponse getChatRoomList(CustomUserDetails customUser) {

        ChatRoomType type = null;
        MbtiType mbti = null;
        if (customUser == null) {
            type = ChatRoomType.GUEST;
        } else {
            type = ChatRoomType.ALL;
            if (customUser.getUser().getMbtiEntity() != null) {
                mbti = customUser.getUser().getMbtiEntity().getResult();
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
        ChatRoomEntity chatRoomEntity = roomRepository.findById(chatRoomId);
        ChatMessageEntity chatMessageEntity = ChatMessageEntity.builder().message(req.getMessage()).sender(req.getSender()).chatRoomEntity(chatRoomEntity).build();
        messageRepository.save(chatMessageEntity);
    }*/
}
