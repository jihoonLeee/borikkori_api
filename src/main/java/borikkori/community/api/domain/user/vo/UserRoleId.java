package borikkori.community.api.domain.user.vo;

import java.util.Objects;

import lombok.Getter;

@Getter
public class UserRoleId {
	private final Long id;

	public UserRoleId(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("UserId cannot be null");
		}
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserRoleId))
			return false;
		UserRoleId userRoleId = (UserRoleId)o;
		return id.equals(userRoleId.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
