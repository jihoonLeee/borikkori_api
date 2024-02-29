package wagwagt.community.api.interfaces.controller.repositories;

import wagwagt.community.api.entities.domain.EmailVerification;


public interface EmailVerificationRepository {


     void verification(EmailVerification emailVerification);
     EmailVerification findLast(String email);

}
