package borikkori.community.api.domain.user.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_authority")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_authority_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "authority_id", nullable = false)
    private Authority authority;

    @Column(name = "granted_date", nullable = false)
    private LocalDateTime grantedDate;

    @Column(name = "status", nullable = false)
    private String status; // 예: ACTIVE, REVOKED 등
}
