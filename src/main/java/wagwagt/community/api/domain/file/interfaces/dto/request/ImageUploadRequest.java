package wagwagt.community.api.domain.file.interfaces.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageUploadRequest {


    private Long postId;

    private MultipartFile file;
}
