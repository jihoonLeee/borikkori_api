package wagwagt.community.api.domain.file.usecases;

import wagwagt.community.api.domain.file.interfaces.dto.request.ImageUploadRequest;

import java.io.IOException;

public interface FileUseCase {

    String upload(ImageUploadRequest req) throws IOException;

    void cleanupImage();

}
