package borikkori.community.api.infrastructures.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.domain.user.entities.Authority;
import borikkori.community.api.domain.user.entities.enums.Role;
import borikkori.community.api.domain.user.interfaces.repositories.AuthorityRepository;

@Component
@RequiredArgsConstructor
public class AuthorityInitializer implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (authorityRepository.count() == 0) { // 권한이 없을 경우만 초기화
            authorityRepository.save(new Authority( Role.USER));
            authorityRepository.save(new Authority( Role.ADMIN));
        }
    }
}