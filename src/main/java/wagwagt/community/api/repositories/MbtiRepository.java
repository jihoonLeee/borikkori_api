package wagwagt.community.api.repositories;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.entities.Mbti;
import wagwagt.community.api.entities.MbtiResult;
import wagwagt.community.api.entities.Post;
import wagwagt.community.api.enums.MbtiType;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MbtiRepository {

    private final EntityManager em;

    public void save(Mbti mbti){
        em.persist(mbti);
    }

    public List<MbtiResult> findTopX(int X){
        return em.createQuery("select m from MbtiResult m order by m.count desc", MbtiResult.class)
                .setMaxResults(X)
                .getResultList();
    }

    public MbtiResult findByResult(MbtiType type){
        return em.createQuery("select m from MbtiResult m  WHERE m.type = :type", MbtiResult.class)
                .setParameter("type",type)
                .getSingleResult();
    }


}
