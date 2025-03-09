package borikkori.community.api.domain.file.repository;

import borikkori.community.api.adapter.out.persistence.file.entity.ImageEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;

import java.util.List;

public interface FileRepository {

    void save(ImageEntity imageEntity);

    ImageEntity findById(Long id);

    List<ImageEntity> findByPost(PostEntity postEntity);

    List<ImageEntity> findUnusedImages();

    void delete(Long id);
}
