package wagwagt.community.api.usecase;

import org.springframework.web.multipart.MultipartFile;
import wagwagt.community.api.entities.domain.Image;

import java.io.IOException;
import java.util.List;

public interface FileUseCase {

    String upload(MultipartFile file) throws IOException;

    void cleanupImage();

}
