package wagwagt.community.api.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostWriteRequest {

    private String title;
    private String contents;
    private String email;
}
