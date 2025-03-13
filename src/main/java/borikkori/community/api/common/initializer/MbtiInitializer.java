package borikkori.community.api.common.initializer;

import borikkori.community.api.adapter.out.persistence.user.entity.RoleEntity;
import borikkori.community.api.common.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MbtiInitializer implements CommandLineRunner {
    @Override
    @Transactional
    public void run(String... args) throws Exception {

    }
}
