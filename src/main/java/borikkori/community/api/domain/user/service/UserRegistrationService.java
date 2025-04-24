package borikkori.community.api.domain.user.service;

import borikkori.community.api.domain.user.entity.User;

public interface UserRegistrationService {
	User registerNewUser(String name, String email, String rawPassword);

	void duplicateUser(String email);
}
