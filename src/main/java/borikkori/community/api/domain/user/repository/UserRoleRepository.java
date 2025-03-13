package borikkori.community.api.domain.user.repository;

import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.entity.UserRole;

public interface UserRoleRepository {
    void save(UserRole userRole);

}
