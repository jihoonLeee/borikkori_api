package borikkori.community.api.domain.user.vo;

import lombok.Getter;

import java.util.Objects;

@Getter
public class UserId {
    private final Long id;

    public UserId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId)) return false;
        UserId userId = (UserId) o;
        return id.equals(userId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
