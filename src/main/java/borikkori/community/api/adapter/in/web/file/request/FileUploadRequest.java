package borikkori.community.api.adapter.in.web.file.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileUploadRequest {

	private Long postId;

	private MultipartFile file;
}
