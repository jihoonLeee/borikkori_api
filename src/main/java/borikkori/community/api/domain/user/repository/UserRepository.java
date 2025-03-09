package borikkori.community.api.domain.user.repository;


import borikkori.community.api.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    List<User> findAll();

    Optional<User> findById(Long id );

    Optional<User> findByEmail(String email);

    void delete(User user);

}
