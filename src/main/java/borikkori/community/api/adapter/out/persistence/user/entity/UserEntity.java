package borikkori.community.api.adapter.out.persistence.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean active = true;  // 계정 활성화 상태

    @Column(nullable = false)
    private boolean emailVerified = false; // 이메일 인증 여부

    @Column(nullable = false)
    private boolean accountLocked = false; // 계정 잠금 여부

    @Column(nullable = false)
    private int failedLoginAttempts = 0; // 로그인 실패 횟수

    private LocalDateTime lastLoginDate; // 마지막 로그인 시간

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updDate;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "mbti_id", nullable = true)
    private MbtiEntity mbtiEntity;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserRoleEntity> userRoles = new ArrayList<>();

    // 연관된 역할 정보를 반환하는 헬퍼 메서드
    public List<RoleEntity> getAuth() {
        return userRoles.stream()
                .map(UserRoleEntity::getRole)
                .collect(Collectors.toList());
    }
    // 생성 메서드: 새로운 사용자를 생성하면서 ID를 생성하고 기본 상태를 설정합니다.
    public static UserEntity create(String email, String name, String encodedPassword) {
        UserEntity user = new UserEntity();
        user.email = email;
        user.name = name;
        user.password = encodedPassword;
        user.active = true;
        user.emailVerified = false;
        user.accountLocked = false;
        user.failedLoginAttempts = 0;
        return user;
    }

    // 연관관계 편의 메서드
    public void setMbtiEntity(MbtiEntity mbtiEntity) {
        this.mbtiEntity = mbtiEntity;
        if (mbtiEntity != null) {
            mbtiEntity.setUser(this);
        }
    }
    public void updateMbtiEntity(MbtiEntity mbtiEntity) {
        this.mbtiEntity = mbtiEntity;
    }
}