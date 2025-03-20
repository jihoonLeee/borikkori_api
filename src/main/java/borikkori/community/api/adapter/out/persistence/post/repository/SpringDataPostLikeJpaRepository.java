package borikkori.community.api.adapter.out.persistence.post.repository;

import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPostLikeJpaRepository extends JpaRepository<PostLikeEntity,Long> {


}
