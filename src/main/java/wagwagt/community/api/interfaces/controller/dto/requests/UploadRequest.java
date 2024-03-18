package wagwagt.community.api.interfaces.controller.dto.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import wagwagt.community.api.entities.domain.User;

@Getter
@Setter
public class UploadRequest {


    private Long postId;

    private MultipartFile file;
}
