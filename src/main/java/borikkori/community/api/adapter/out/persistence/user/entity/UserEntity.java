package borikkori.community.api.adapter.out.persistence.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mbti_id")
    private MbtiEntity mbtiEntity;

    // 생성 메서드
    public static UserEntity create(String email, String name, String password, PasswordEncoder encoder) {
        UserEntity user = new UserEntity();
        user.email = email;
        user.name = name;
        user.password = encoder.encode(password); // 비밀번호 해시 처리
        return user;
    }

    // 연관관계 편의 메서드
    public void setMbtiEntity(MbtiEntity mbtiEntity) {
        this.mbtiEntity = mbtiEntity;
        if (mbtiEntity != null) {
            mbtiEntity.setUser(this);
        }
    }

    // 비밀번호 변경 메서드
    public void changePassword(String newPassword, PasswordEncoder encoder) {
        this.password = encoder.encode(newPassword); // 비밀번호 해시 처리
    }
}