package borikkori.community.api.application.domain.file.usecase;

import borikkori.community.api.adapter.in.web.file.request.FileUploadRequest;

import java.io.IOException;

public interface FileUseCase {

    String upload(FileUploadRequest req) throws IOException;

    void cleanupFiles();

}
