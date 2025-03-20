package borikkori.community.api.adapter.out.persistence.file.repository;

import borikkori.community.api.adapter.out.persistence.file.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SpringDataFileJpaRepository extends JpaRepository<FileEntity,Long> {
}
