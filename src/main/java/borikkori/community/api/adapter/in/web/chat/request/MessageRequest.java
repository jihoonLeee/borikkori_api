package borikkori.community.api.adapter.in.web.chat.request;

import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {

	private String message;
	private UserEntity sender;

}
