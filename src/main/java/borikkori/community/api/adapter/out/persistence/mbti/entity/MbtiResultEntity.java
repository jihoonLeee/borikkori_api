package borikkori.community.api.adapter.out.persistence.mbti.entity;

import jakarta.persistence.*;
import lombok.Getter;
import borikkori.community.api.common.enums.MbtiType;

@Entity
@Getter
public class MbtiResultEntity {

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
