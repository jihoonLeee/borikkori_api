package wagwagt.community.api.interfaces.controller.repositories;

import wagwagt.community.api.entities.domain.Image;

import java.time.LocalDateTime;
import java.util.List;

public interface FileRepository {

    void save(Image image);

    Image findById(Long id);

    List<Image> findUnusedImages();

    void delete(Long id);
}
