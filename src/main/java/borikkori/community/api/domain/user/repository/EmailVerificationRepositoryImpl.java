package borikkori.community.api.domain.user.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import borikkori.community.api.adapter.out.persistence.user.entity.EmailVerificationEntity;

@Repository
@RequiredArgsConstructor
public class EmailVerificationRepositoryImpl implements EmailVerificationRepository {

    private final EntityManager em;

    public void verification(EmailVerificationEntity emailVerificationEntity){
        em.persist(emailVerificationEntity);
    }
    public EmailVerificationEntity findLast(String email){
        TypedQuery<EmailVerificationEntity> query = em.createQuery("select v from EmailVerification v " +
                "where v.userEmail = :email order by v.createDate desc", EmailVerificationEntity.class);
        query.setParameter("email", email);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

}
