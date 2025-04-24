package borikkori.community.api.application.domain.file.usecase;

import java.io.IOException;

import borikkori.community.api.adapter.in.web.file.request.FileUploadRequest;

public interface FileUseCase {

	String upload(FileUploadRequest req) throws IOException;

	void cleanupFiles();

}
