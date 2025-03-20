package borikkori.community.api.adapter.out.persistence.file.adapter;

import borikkori.community.api.adapter.out.persistence.file.repository.SpringDataFileJpaRepository;
import borikkori.community.api.adapter.out.persistence.user.entity.EmailVerificationEntity;
import borikkori.community.api.adapter.out.persistence.user.repository.SpringDataEmailVerificationJpaRepository;
import borikkori.community.api.common.exeptions.EntityNotFoundException;
import borikkori.community.api.domain.file.repository.FileRepository;
import borikkori.community.api.domain.user.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FileRepositoryAdapter implements FileRepository {

    private final SpringDataFileJpaRepository fileJpaRepository;


}
