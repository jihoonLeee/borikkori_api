package borikkori.community.api.domain.post.interfaces.dto.request;

import lombok.Getter;
import lombok.Setter;
import borikkori.community.api.domain.user.entities.User;

@Getter
@Setter
public class PostWriteRequest {

    private Long postId;
    private String title;
    private String contents;
    private User user;
}
