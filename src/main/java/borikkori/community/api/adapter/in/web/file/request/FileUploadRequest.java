package borikkori.community.api.adapter.in.web.file.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadRequest {

	private Long postId;

	private MultipartFile file;
}
