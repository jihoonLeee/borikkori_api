package wagwagt.community.api.usecase;

import org.springframework.web.multipart.MultipartFile;
import wagwagt.community.api.entities.domain.Image;
import wagwagt.community.api.interfaces.controller.dto.requests.UploadRequest;

import java.io.IOException;
import java.util.List;

public interface FileUseCase {

    String upload(UploadRequest req) throws IOException;

    void cleanupImage();

}
