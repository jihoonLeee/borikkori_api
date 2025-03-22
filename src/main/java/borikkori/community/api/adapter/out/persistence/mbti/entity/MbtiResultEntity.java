package borikkori.community.api.adapter.out.persistence.mbti.entity;

import jakarta.persistence.*;
import lombok.Getter;
import borikkori.community.api.common.enums.MbtiType;
import lombok.Setter;

@Entity
@Getter
@Table(name="mbti_result")
public class MbtiResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Setter
    private MbtiType type;

    @Setter
    private Long count = 0L;


}
