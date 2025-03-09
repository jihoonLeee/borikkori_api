package borikkori.community.api.adapter.out.persistence.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_verification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailVerificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private int verificationNumber;

    @Column(name = "is_success")
    private boolean isSuccess;

    @CreatedDate
    private LocalDateTime regDate;

    // 정적 팩토리 메서드
    public static EmailVerificationEntity create(UserEntity user, int verificationNumber, LocalDateTime regDate) {
        EmailVerificationEntity verification = new EmailVerificationEntity();
        verification.user = user;
        verification.verificationNumber = verificationNumber;
        verification.regDate = regDate;
        verification.isSuccess = false; // 기본값 설정
        return verification;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}