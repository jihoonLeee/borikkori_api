package borikkori.community.api.adapter.in.web.file.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileUploadRequest {

    private Long postId;

    private MultipartFile file;
}
