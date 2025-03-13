package borikkori.community.api.common.initializer;

import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.common.enums.Role;
import borikkori.community.api.domain.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthorityInitializer implements CommandLineRunner {

    private final RoleRepository authorityRepository;
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (authorityRepository.count() == 0) { // 권한이 없을 경우만 초기화
            authorityRepository.save(new RoleEntity( Role.USER));
            authorityRepository.save(new RoleEntity( Role.ADMIN));
        }
    }
}