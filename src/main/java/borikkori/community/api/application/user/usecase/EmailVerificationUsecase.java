package borikkori.community.api.application.user.usecase;

import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;

public interface EmailVerificationUsecase {

    // 이메일 전송
    int sendEmail(UserEntity userEntity);

    // 이메일 검증
    boolean checkEmail(int verifyCode,String email);
}
