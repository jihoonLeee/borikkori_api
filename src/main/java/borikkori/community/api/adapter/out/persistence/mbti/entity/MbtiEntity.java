package borikkori.community.api.adapter.out.persistence.mbti.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.common.enums.MbtiType;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="user_mbti")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MbtiEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mbti_id")
    private Long id;

    @OneToOne(mappedBy = "mbtiEntity",fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = true)
    @Setter
    private UserEntity user;

    private String testName;

    @Enumerated(EnumType.STRING)
    private MbtiType result;

    @CreatedDate
    private LocalDateTime testDate;

}
