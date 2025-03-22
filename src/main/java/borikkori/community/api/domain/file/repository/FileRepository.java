package borikkori.community.api.domain.file.repository;

import borikkori.community.api.domain.file.entity.File;
import borikkori.community.api.domain.post.entity.Post;

import java.util.List;

public interface FileRepository {

    void saveFile(File file);

    File findFileById(Long id);

    List<File> findFilesByPost(Post post);

    List<File> findUnusedFiles();

    void deleteFile(Long id);

}
