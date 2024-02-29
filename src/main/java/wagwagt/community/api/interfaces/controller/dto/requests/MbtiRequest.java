package wagwagt.community.api.interfaces.controller.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wagwagt.community.api.entities.domain.enums.MbtiType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MbtiRequest {

    private String email;

    private MbtiType result;
}
