package wagwagt.community.api.domain.user.usecases.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wagwagt.community.api.domain.user.entities.EmailVerification;
import wagwagt.community.api.common.util.RandomCodeGenerator;
import wagwagt.community.api.common.service.EmailSender;
import wagwagt.community.api.domain.user.interfaces.repositories.EmailVerificationRepository;
import wagwagt.community.api.domain.user.usecases.EmailVerificationUsecase;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationUsecaseImpl implements EmailVerificationUsecase {

    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailSender emailSender;

    @Override
    @Transactional
    public int sendEmail(String email) {
        int code = RandomCodeGenerator.generate();
        EmailVerification emailVerification = EmailVerification.
                builder()
                .userEmail(email)
                .verificationNumber(code)
                .isSuccess(false)
                .createDate(LocalDateTime.now())
                .build();
         /**
          * 이메일 전송하기 로직  TODO: 예외처리
          * */
        System.out.println(email + "  :  " + code);
        emailSender.send(email,code);

        emailVerificationRepository.verification(emailVerification); // 전송 성공하면 저장
        return code;
    }

    @Override
    @Transactional
    public boolean checkEmail(int verifyCode,String email) {
        EmailVerification ev = emailVerificationRepository.findLast(email);
        if(verifyCode == ev.getVerificationNumber() && !ev.isSuccess()){
            ev.setSuccess(true);
            return true;
        }else{
            throw new IllegalStateException("안맞음");
        }
    }
}
