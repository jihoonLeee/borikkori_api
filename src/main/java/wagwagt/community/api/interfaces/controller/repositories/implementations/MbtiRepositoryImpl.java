package wagwagt.community.api.interfaces.controller.repositories.implementations;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.entities.domain.Mbti;
import wagwagt.community.api.entities.domain.MbtiResult;
import wagwagt.community.api.entities.domain.enums.MbtiType;
import wagwagt.community.api.interfaces.controller.repositories.MbtiRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MbtiRepositoryImpl implements MbtiRepository {

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
