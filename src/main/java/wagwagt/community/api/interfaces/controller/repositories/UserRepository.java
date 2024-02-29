package wagwagt.community.api.interfaces.controller.repositories;


import wagwagt.community.api.entities.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {


    void save(User user);

    List<User> findAll();
    User findById(Long id );

    Optional<User> findByEmail(String email);

    void delete(User user);

}
