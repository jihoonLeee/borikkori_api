package borikkori.community.api.adapter.out.persistence.user.entity;

import borikkori.community.api.common.enums.RoleStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoleStatus status;

    public static UserRoleEntity create(UserEntity user, RoleEntity role, RoleStatus status) {
        UserRoleEntity userAuthority = new UserRoleEntity();
        userAuthority.user = user;
        userAuthority.role = role;
        userAuthority.status = status;
        return userAuthority;
    }

    public void updateStatus(RoleStatus status) {
        this.status = status;
    }
}