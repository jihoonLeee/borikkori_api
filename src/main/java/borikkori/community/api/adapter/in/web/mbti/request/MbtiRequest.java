package borikkori.community.api.adapter.in.web.mbti.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import borikkori.community.api.common.enums.MbtiType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MbtiRequest {

    private String email;
    private MbtiType result;
}
