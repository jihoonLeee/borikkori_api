package borikkori.community.api.domain.file.interfaces.repositories;

import borikkori.community.api.domain.file.entities.Image;
import borikkori.community.api.domain.post.entities.Post;

import java.util.List;

public interface FileRepository {

    void save(Image image);

    Image findById(Long id);

    List<Image> findByPost(Post post);

    List<Image> findUnusedImages();

    void delete(Long id);
}
