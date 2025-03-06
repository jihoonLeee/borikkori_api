package borikkori.community.api.domain.user.interfaces.repositories;

import borikkori.community.api.domain.user.entities.Authority;
import borikkori.community.api.domain.user.entities.enums.Role;

import java.util.Optional;

public interface AuthorityRepository {
    void save(Authority auth);
    Authority findOne(Long id);

    Optional<Authority> findByRole(Role role);

    long count() ;
}
