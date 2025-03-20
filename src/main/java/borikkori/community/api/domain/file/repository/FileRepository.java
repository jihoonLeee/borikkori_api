package borikkori.community.api.domain.file.repository;

import borikkori.community.api.adapter.out.persistence.file.entity.FileEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;

import java.util.List;

public interface FileRepository {

    void saveFile(File file);

    File findFileById(Long id);

    List<File> findFilesByPost(Post post);

    List<File> findUnusedImages();

    void deleteFile(Long id);
}
