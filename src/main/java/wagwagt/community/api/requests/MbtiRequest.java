package wagwagt.community.api.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wagwagt.community.api.enums.MbtiType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MbtiRequest {

    private String email;

    private MbtiType result;
}
