package borikkori.community.api.application.user.usecase;

import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.adapter.out.persistence.user.entity.EmailVerificationEntity;
import borikkori.community.api.common.util.RandomCodeGenerator;
import borikkori.community.api.domain.user.service.EmailSender;
import borikkori.community.api.domain.user.repository.EmailVerificationRepository;

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
    public int sendEmail(UserEntity userEntity) {
        int code = RandomCodeGenerator.generate();
        EmailVerificationEntity emailVerificationEntity = EmailVerificationEntity.
              create(userEntity,code,LocalDateTime.now());

         /**
          * 이메일 전송하기 로직  TODO: 예외처리
          * */
        System.out.println(userEntity.getEmail() + "  :  " + code);
        emailSender.send(userEntity.getEmail(),code);

        emailVerificationRepository.verification(emailVerificationEntity); // 전송 성공하면 저장
        return code;
    }

    @Override
    @Transactional
    public boolean checkEmail(int verifyCode,String email) {
        EmailVerificationEntity ev = emailVerificationRepository.findLast(email);
        if(verifyCode == ev.getVerificationNumber() && !ev.isSuccess()){
            ev.setSuccess(true);
            return true;
        }else{
            throw new IllegalStateException("안맞음");
        }
    }
}
