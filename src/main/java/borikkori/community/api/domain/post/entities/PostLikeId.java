package borikkori.community.api.domain.post.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class PostLikeId implements Serializable {
    private Long post;
    private Long user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostLikeId)) return false;
        PostLikeId that = (PostLikeId) o;
        return Objects.equals(post, that.post) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, user);
    }
}
