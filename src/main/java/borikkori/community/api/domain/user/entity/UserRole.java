package borikkori.community.api.domain.user.entity;

import borikkori.community.api.common.enums.Role;
import borikkori.community.api.common.enums.RoleStatus;
import borikkori.community.api.domain.user.vo.UserId;
import borikkori.community.api.domain.user.vo.UserRoleId;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserRole {

    private UserRoleId id;              // 식별자, 영속화 전에는 null일 수 있음
    private final User user;      // 도메인 User 객체 (도메인 엔티티)
    private final Role role;      // 도메인 Role 객체 (또는 값 객체)
    private RoleStatus status;    // 역할 상태 (예: ENABLED, DISABLED 등)
    private final LocalDateTime regDate;  // 생성 시각
    private LocalDateTime updDate;        // 마지막 수정 시각

    // 생성자는 외부에서 변경되지 않도록 필요한 필드들을 모두 받습니다.
    public UserRole(UserRoleId id, User user, Role role, RoleStatus status,
                    LocalDateTime regDate, LocalDateTime updDate) {
        this.id = id;
        this.user = user;
        this.role = role;
        this.status = status;
        this.regDate = regDate;
        this.updDate = updDate;
    }

    // Factory 메서드를 통해 새 UserRole을 생성합니다.
    // id는 아직 부여되지 않은 상태이므로 null 처리하고, 생성/수정 시간은 현재 시간으로 설정합니다.
    public static UserRole create(User user, Role role, RoleStatus status) {
        LocalDateTime now = LocalDateTime.now();
        return new UserRole(null, user, role, status, now, now);
    }

    // 역할 상태를 변경하는 도메인 로직
    public void updateStatus(RoleStatus newStatus) {
        this.status = newStatus;
        this.updDate = LocalDateTime.now();
    }
}
