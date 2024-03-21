package wagwagt.community.api.domain.chat.usecases.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wagwagt.community.api.domain.chat.usecases.ChatUseCase;

@Service
@Transactional(readOnly = true)
public class ChatUseCaseImpl implements ChatUseCase {

}
