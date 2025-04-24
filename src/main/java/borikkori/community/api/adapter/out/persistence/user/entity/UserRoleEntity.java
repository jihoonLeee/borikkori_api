package borikkori.community.api.adapter.out.persistence.user.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import borikkori.community.api.common.enums.RoleStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime updDate;

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
