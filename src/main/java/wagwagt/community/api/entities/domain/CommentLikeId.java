package wagwagt.community.api.entities.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class CommentLikeId implements Serializable {
    private Long comment;
    private Long user;
}
