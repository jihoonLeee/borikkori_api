package borikkori.community.api.adapter.in.web.mbti.request;

import borikkori.community.api.common.enums.MbtiType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MbtiRequest {

	private String email;
	private MbtiType result;
}
