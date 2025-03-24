package borikkori.community.api.domain.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;

import borikkori.community.api.common.enums.MbtiType;
import borikkori.community.api.common.enums.Role;
import borikkori.community.api.domain.user.vo.UserId;
import lombok.Getter;

@Getter
public class User {
	private final UserId id;
	private final List<Role> roles;
	private String name;
	private String email;
	private String password;
	private boolean active;
	private boolean emailVerified;
	private boolean accountLocked;
	private int failedLoginAttempts;
	private LocalDateTime lastLoginDate;
	private final LocalDateTime regDate;
	private LocalDateTime updDate;
	private MbtiType mbtiResult;

	public User(UserId id, String name, String email, String password, boolean active, boolean emailVerified,
		boolean accountLocked, int failedLoginAttempts, LocalDateTime lastLoginDate, LocalDateTime regDate,
		LocalDateTime updDate, List<Role> roles, MbtiType mbtiResult) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name must not be empty");
		}
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Email must not be empty");
		}
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Password must not be empty");
		}
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.active = active;
		this.emailVerified = emailVerified;
		this.accountLocked = accountLocked;
		this.failedLoginAttempts = failedLoginAttempts;
		this.lastLoginDate = lastLoginDate;
		this.regDate = regDate != null ? regDate : LocalDateTime.now();
		this.updDate = updDate != null ? updDate : LocalDateTime.now();
		this.roles = roles;
		this.mbtiResult = mbtiResult;
	}

	public static User create(String name, String email, String encodedPassword) {
		LocalDateTime now = LocalDateTime.now();
		List<Role> defaultRoles = new ArrayList<>();
		defaultRoles.add(Role.USER);
		return new User(null, name, email, encodedPassword, true, false, false, 0, null, now, now, defaultRoles, null);
	}

	public List<String> getRole() {
		if (roles == null) {
			return List.of();
		}
		return roles.stream().map(Role::getRole).collect(Collectors.toList());
	}

	public void addRole(Role role) {
		// roles 리스트가 final 이라면, 초기화된 리스트가 수정 가능하도록 해야 합니다.
		this.roles.add(role);
	}

	public boolean hasRole(Role role) {
		return this.roles.contains(role);
	}

	public void changePassword(String newPassword, PasswordEncoder encoder) {
		this.password = encoder.encode(newPassword);
		updateModificationDate();
	}

	public void updateLastLogin() {
		this.lastLoginDate = LocalDateTime.now();
		this.failedLoginAttempts = 0;
		updateModificationDate();
	}

	public void incrementFailedLoginAttempts() {
		this.failedLoginAttempts++;
		if (this.failedLoginAttempts >= 5) {
			this.accountLocked = true;
		}
		updateModificationDate();
	}

	public void unlockAccount() {
		this.accountLocked = false;
		this.failedLoginAttempts = 0;
		updateModificationDate();
	}

	private void updateModificationDate() {
		this.updDate = LocalDateTime.now();
	}

}
