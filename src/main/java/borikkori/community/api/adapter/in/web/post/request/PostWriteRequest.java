package borikkori.community.api.adapter.in.web.post.request;

import lombok.Getter;
import lombok.Setter;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;

@Getter
@Setter
public class PostWriteRequest {

    private Long postId;
    private String title;
    private String contents;
    private UserEntity user;
}
