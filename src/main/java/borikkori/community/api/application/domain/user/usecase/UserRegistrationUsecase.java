package borikkori.community.api.application.domain.user.usecase;

import borikkori.community.api.adapter.in.web.user.request.JoinRequest;
import borikkori.community.api.common.enums.MbtiType;

import java.util.Optional;

public interface UserRegistrationUsecase {
    Long joinUser(JoinRequest req, Optional<MbtiType> mbtiTypeOpt);
}
