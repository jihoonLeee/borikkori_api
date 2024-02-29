package wagwagt.community.api.entities.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name="email_verification")
@Getter
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_id")
    private Long id;

    private String userEmail;

    private int verificationNumber;

    @Column(name = "is_success")
    @Setter
    private boolean isSuccess;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createDate;

    protected EmailVerification() {
        // JPA를 위한 기본 생성자
    }

    @Builder
    public EmailVerification(Long id, String userEmail, int verificationNumber, boolean isSuccess, LocalDateTime createDate) {
        // 인자를 받는 생성자
        this.id = id;
        this.userEmail = userEmail;
        this.verificationNumber = verificationNumber;
        this.isSuccess = isSuccess;
        this.createDate = createDate;
    }
}
