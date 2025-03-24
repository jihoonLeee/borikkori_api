package borikkori.community.api.adapter.out.persistence.user.entity;

import org.springframework.security.core.GrantedAuthority;

import borikkori.community.api.common.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleEntity implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(unique = true, nullable = false)
	private Role role;

	private RoleEntity(Role role) {
		this.role = role;
	}

	public static RoleEntity create(Role role) {
		return new RoleEntity(role);
	}

	@Override
	public String getAuthority() {
		return this.role.getRole(); // Spring Security에서 권한 문자열로 사용
	}
}
