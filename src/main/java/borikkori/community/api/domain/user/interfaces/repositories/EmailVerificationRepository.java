package borikkori.community.api.domain.user.interfaces.repositories;

import borikkori.community.api.domain.user.entities.EmailVerification;


public interface EmailVerificationRepository {


     void verification(EmailVerification emailVerification);
     EmailVerification findLast(String email);

}
