package borikkori.community.api.adapter.out.persistence.post.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class CommentLikeIdEntity implements Serializable {
    private Long comment;
    private Long user;
}
