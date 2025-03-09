package borikkori.community.api.adapter.in.web.chat.request;

import lombok.Getter;
import lombok.Setter;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;

@Getter
@Setter
public class MessageRequest {

    private String message;
    private UserEntity sender;

}
