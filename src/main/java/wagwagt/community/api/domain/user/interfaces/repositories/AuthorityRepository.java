package wagwagt.community.api.domain.user.interfaces.repositories;

import wagwagt.community.api.domain.user.entities.Authority;

public interface AuthorityRepository {
    void save(Authority auth);
    Authority findOne(Long id);
}
