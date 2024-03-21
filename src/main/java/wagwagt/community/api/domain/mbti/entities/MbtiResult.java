package wagwagt.community.api.domain.mbti.entities;

import jakarta.persistence.*;
import lombok.Getter;
import wagwagt.community.api.common.enums.MbtiType;

@Entity
@Getter
public class MbtiResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MbtiType type;

    private Long count = 0L;


    public void setCount(Long count) {
        this.count = count;
    }
}
