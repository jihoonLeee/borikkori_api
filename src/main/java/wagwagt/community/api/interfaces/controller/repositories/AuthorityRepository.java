package wagwagt.community.api.interfaces.controller.repositories;

import wagwagt.community.api.entities.domain.Authority;

public interface AuthorityRepository {
    void save(Authority auth);
    Authority findOne(Long id);
}
