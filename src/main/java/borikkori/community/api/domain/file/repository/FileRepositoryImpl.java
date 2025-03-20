package borikkori.community.api.domain.file.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import borikkori.community.api.adapter.out.persistence.file.entity.FileEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.common.enums.FileType;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {

    private final EntityManager em;

    @Override
    public void save(FileEntity fileEntity){
        em.persist(fileEntity);
    }

    @Override
    public FileEntity findById(Long id) {
        return em.find(FileEntity.class,id);
    }

    @Override
    public List<FileEntity> findUnusedImages() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        return em.createQuery("SELECT i FROM Image i WHERE i.imageStatus = :imageStatus AND i.regDate < :oneHourAgo", FileEntity.class)
                .setParameter("imageStatus", FileType.DRAFT)
                .setParameter("oneHourAgo", oneHourAgo)
                .getResultList();
    }

    @Override
    public List<FileEntity> findByPost(PostEntity postEntity){
        return em.createQuery("SELECT i FROM Image i where i.post = :post and i.imageStatus = :imageStatus", FileEntity.class)
                .setParameter("imageStatus", FileType.DRAFT)
                .setParameter("post", postEntity)
                .getResultList();
    }

    @Override
    public void delete(Long id) {
        FileEntity fileEntity = findById(id);
        if(fileEntity != null){
            em.remove(fileEntity);
        }

    }
}
//