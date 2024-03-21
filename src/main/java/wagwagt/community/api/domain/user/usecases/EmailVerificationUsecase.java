package wagwagt.community.api.domain.user.usecases;

public interface EmailVerificationUsecase {

    // 이메일 전송
    int sendEmail(String email);

    // 이메일 검증
    boolean checkEmail(int verifyCode,String email);
}
