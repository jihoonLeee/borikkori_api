package wagwagt.community.api.entities.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class PostLikeId implements Serializable {
    private Long post;
    private Long user;
}
