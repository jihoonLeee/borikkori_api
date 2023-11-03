package wagwagt.community.api.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wagwagt.community.api.entities.EmailVerification;
import wagwagt.community.api.entities.User;

@Repository
@RequiredArgsConstructor
public class EmailVerificationRepository {

    private final EntityManager em;

    public void verification(EmailVerification emailVerification){
        em.persist(emailVerification);
    }
    public EmailVerification findLast(String email){
        TypedQuery<EmailVerification> query = em.createQuery("select v from EmailVerification v " +
                "where v.userEmail = :email order by v.createDate desc", EmailVerification.class);
        query.setParameter("email", email);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

}
