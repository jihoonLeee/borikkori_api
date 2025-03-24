package borikkori.community.api.application.domain.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import borikkori.community.api.config.security.CustomUserDetails;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.repository.UserRepository;

@Service
public class UserContextService {

	private final UserRepository userRepository;

	public UserContextService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * SecurityContextHolder에서 현재 인증된 사용자의 도메인 User 객체를 반환합니다.
	 * 만약 인증된 사용자가 없으면 예외를 발생시킵니다.
	 */
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() ||
			!(authentication.getPrincipal() instanceof CustomUserDetails)) {
			throw new RuntimeException("현재 인증된 사용자가 없습니다.");
		}
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		return userDetails.getUser();
	}
}
