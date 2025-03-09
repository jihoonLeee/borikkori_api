package borikkori.community.api.application.file.usecase;

import borikkori.community.api.adapter.in.web.file.request.ImageUploadRequest;

import java.io.IOException;

public interface FileUseCase {

    String upload(ImageUploadRequest req) throws IOException;

    void cleanupImage();

}
