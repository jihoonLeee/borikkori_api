package borikkori.community.api.domain.user.usecases;

import borikkori.community.api.domain.user.entities.User;
import borikkori.community.api.domain.user.interfaces.dto.request.JoinRequest;

import java.util.Optional;

public interface UserManagementUsecase {
    Long join(JoinRequest req);
    Optional<User> findOne(Long userId);
    Optional<User> findByEmail(String email);
}
