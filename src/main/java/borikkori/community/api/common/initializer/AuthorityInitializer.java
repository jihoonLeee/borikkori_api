package borikkori.community.api.common.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import borikkori.community.api.common.enums.Role;
import borikkori.community.api.domain.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthorityInitializer implements CommandLineRunner {

	private final RoleRepository roleRepository;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		if (roleRepository.countRole() == 0) { // 권한이 없을 경우만 초기화
			roleRepository.saveRole(Role.USER);
			roleRepository.saveRole(Role.ADMIN);
		}
	}
}
