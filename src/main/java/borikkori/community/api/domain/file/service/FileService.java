package borikkori.community.api.domain.file.service;

import borikkori.community.api.common.enums.FileStatus;
import borikkori.community.api.common.enums.FileType;
import borikkori.community.api.domain.file.entity.File;
import borikkori.community.api.domain.post.entity.Post;

public interface FileService {

	void activateFilesForPost(Post post);

	File createFile(String originalName,
		String extension,
		String savedName,
		String savedUrl,
		long fileSize,
		FileType fileType,
		String contentType,
		Integer duration,
		String resolution,
		String metadata);

	void updateFile(File file, String contentType, Integer duration, String resolution, String metadata,
		FileStatus newStatus);
}
