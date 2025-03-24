package borikkori.community.api.domain.file.repository;

import java.util.List;

import borikkori.community.api.domain.file.entity.File;
import borikkori.community.api.domain.post.entity.Post;

public interface FileRepository {

	void saveFile(File file);

	File findFileById(Long id);

	List<File> findFilesByPost(Post post);

	List<File> findUnusedFiles();

	void deleteFile(Long id);

}
