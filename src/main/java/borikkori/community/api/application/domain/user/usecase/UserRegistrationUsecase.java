package borikkori.community.api.application.domain.user.usecase;

import java.util.Optional;

import borikkori.community.api.adapter.in.web.user.request.JoinRequest;
import borikkori.community.api.common.enums.MbtiType;

public interface UserRegistrationUsecase {
	Long joinUser(JoinRequest req, Optional<MbtiType> mbtiTypeOpt);
}
