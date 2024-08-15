package wagwagt.community.api.domain.user.interfaces.repositories;


import wagwagt.community.api.domain.user.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {


    void save(User user);

    List<User> findAll();
    Optional<User> findById(Long id );

    Optional<User> findByEmail(String email);

    void delete(User user);

}
