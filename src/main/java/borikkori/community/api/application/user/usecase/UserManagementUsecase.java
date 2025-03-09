package borikkori.community.api.application.user.usecase;

import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.in.web.user.request.JoinRequest;

import java.util.Optional;

public interface UserManagementUsecase {
    Long join(JoinRequest req);
    Optional<UserEntity> findOne(Long userId);
    Optional<UserEntity> findByEmail(String email);
}
