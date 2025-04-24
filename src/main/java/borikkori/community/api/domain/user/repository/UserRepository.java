package borikkori.community.api.domain.user.repository;

import java.util.List;
import java.util.Optional;

import borikkori.community.api.domain.user.entity.User;

public interface UserRepository {

	User saveUser(User user);

	List<User> findAll();

	Optional<User> findById(Long id);

	Optional<User> findByEmail(String email);

	void delete(User user);

}
