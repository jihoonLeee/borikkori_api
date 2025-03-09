package borikkori.community.api.domain.file.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import borikkori.community.api.adapter.out.persistence.file.entity.ImageEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.common.enums.ImageStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {

    private final EntityManager em;

    @Override
    public void save(ImageEntity imageEntity){
        em.persist(imageEntity);
    }

    @Override
    public ImageEntity findById(Long id) {
        return em.find(ImageEntity.class,id);
    }

    @Override
    public List<ImageEntity> findUnusedImages() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        return em.createQuery("SELECT i FROM Image i WHERE i.imageStatus = :imageStatus AND i.regDate < :oneHourAgo", ImageEntity.class)
                .setParameter("imageStatus", ImageStatus.DRAFT)
                .setParameter("oneHourAgo", oneHourAgo)
                .getResultList();
    }

    @Override
    public List<ImageEntity> findByPost(PostEntity postEntity){
        return em.createQuery("SELECT i FROM Image i where i.post = :post and i.imageStatus = :imageStatus", ImageEntity.class)
                .setParameter("imageStatus", ImageStatus.DRAFT)
                .setParameter("post", postEntity)
                .getResultList();
    }

    @Override
    public void delete(Long id) {
        ImageEntity imageEntity = findById(id);
        if(imageEntity != null){
            em.remove(imageEntity);
        }

    }
}
//