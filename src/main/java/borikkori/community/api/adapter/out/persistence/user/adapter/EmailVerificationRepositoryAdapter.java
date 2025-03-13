package borikkori.community.api.adapter.out.persistence.user.adapter;

import borikkori.community.api.adapter.out.persistence.user.entity.EmailVerificationEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserRoleEntity;
import borikkori.community.api.adapter.out.persistence.user.mapper.UserRoleMapper;
import borikkori.community.api.adapter.out.persistence.user.repository.SpringDataEmailVerificationJpaRepository;
import borikkori.community.api.adapter.out.persistence.user.repository.SpringDataUserRoleJpaRepository;
import borikkori.community.api.common.exeptions.EntityNotFoundException;
import borikkori.community.api.domain.user.entity.UserRole;
import borikkori.community.api.domain.user.repository.EmailVerificationRepository;
import borikkori.community.api.domain.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmailVerificationRepositoryAdapter implements EmailVerificationRepository {

    private final SpringDataEmailVerificationJpaRepository emailVerificationJpaRepository;

    @Override
    public void saveVerification(EmailVerificationEntity emailVerificationEntity) {
        emailVerificationJpaRepository.save(emailVerificationEntity);
    }

    @Override
    public EmailVerificationEntity findLatestVerificationByEmail(String email) {
        return emailVerificationJpaRepository.findTopByUser_EmailOrderByRegDateDesc(email)
                .orElseThrow(() -> new EntityNotFoundException("No email verification found for email: " + email));
    }
}
