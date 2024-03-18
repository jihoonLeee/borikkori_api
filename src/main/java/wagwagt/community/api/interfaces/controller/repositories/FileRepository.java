package wagwagt.community.api.interfaces.controller.repositories;

import wagwagt.community.api.entities.domain.Image;
import wagwagt.community.api.entities.domain.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface FileRepository {

    void save(Image image);

    Image findById(Long id);

    List<Image> findByPost(Post post);

    List<Image> findUnusedImages();

    void delete(Long id);
}
