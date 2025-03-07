package borikkori.community.api.domain.mbti.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import borikkori.community.api.domain.user.entities.User;
import borikkori.community.api.common.enums.MbtiType;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name="mbti")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Mbti {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mbti_id")
    private Long id;

    @Setter
    @OneToOne(mappedBy = "mbti",fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = true)
    private User user;

    private String name;

    @Enumerated(EnumType.STRING)
    private MbtiType result;

    @CreatedDate
    private LocalDateTime testDate;


}
