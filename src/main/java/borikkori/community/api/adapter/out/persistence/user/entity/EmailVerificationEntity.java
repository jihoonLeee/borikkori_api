package borikkori.community.api.adapter.out.persistence.user.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "email_verification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailVerificationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "verification_id")
	private Long id;

	@Column(nullable = false)
	private String email;

	private int verificationNumber;

	@Column(name = "is_success")
	private boolean isSuccess;

	@CreatedDate
	private LocalDateTime regDate;

	// 정적 팩토리 메서드
	public static EmailVerificationEntity create(String email, int verificationNumber) {
		EmailVerificationEntity verification = new EmailVerificationEntity();
		verification.email = email;
		verification.verificationNumber = verificationNumber;
		verification.regDate = LocalDateTime.now();
		verification.isSuccess = false; // 기본값
		return verification;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
}
