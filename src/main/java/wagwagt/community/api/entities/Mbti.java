package wagwagt.community.api.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import wagwagt.community.api.enums.MbtiType;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = true)
    private User user;

    private String name;

    @Enumerated(EnumType.STRING)
    private MbtiType result;

    @CreatedDate
    private LocalDateTime testDate;

}
