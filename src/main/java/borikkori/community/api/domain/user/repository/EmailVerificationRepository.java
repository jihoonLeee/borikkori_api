package borikkori.community.api.domain.user.repository;

import borikkori.community.api.adapter.out.persistence.user.entity.EmailVerificationEntity;


public interface EmailVerificationRepository {

     void verification(EmailVerificationEntity emailVerificationEntity);
     EmailVerificationEntity findLast(String email);

}
