package borikkori.community.api.application.domain.user.usecase;

import borikkori.community.api.adapter.in.web.user.request.JoinRequest;

public interface UserRegistrationUsecase {
    Long joinUser(JoinRequest req);
}
