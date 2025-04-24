package borikkori.community.api.adapter.out.persistence.mbti.entity;

import borikkori.community.api.common.enums.MbtiType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "mbti_result")
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
