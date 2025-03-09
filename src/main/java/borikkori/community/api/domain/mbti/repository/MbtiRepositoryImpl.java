package borikkori.community.api.domain.mbti.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiEntity;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiResultEntity;
import borikkori.community.api.common.enums.MbtiType;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MbtiRepositoryImpl implements MbtiRepository {

    private final EntityManager em;

    public void save(MbtiEntity mbtiEntity){
        em.persist(mbtiEntity);
    }

    public List<MbtiResultEntity> findTopX(int X){
        return em.createQuery("select m from MbtiResult m order by m.count desc", MbtiResultEntity.class)
                .setMaxResults(X)
                .getResultList();
    }

    public MbtiResultEntity findByResult(MbtiType type){
        return em.createQuery("select m from MbtiResult m  WHERE m.type = :type", MbtiResultEntity.class)
                .setParameter("type",type)
                .getSingleResult();
    }
}
