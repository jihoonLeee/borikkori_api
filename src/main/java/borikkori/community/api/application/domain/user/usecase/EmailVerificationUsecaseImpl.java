package borikkori.community.api.application.domain.user.usecase;

import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.out.persistence.user.mapper.UserMapper;
import borikkori.community.api.application.port.EmailServicePort;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.adapter.out.persistence.user.entity.EmailVerificationEntity;
import borikkori.community.api.common.util.RandomCodeGenerator;
import borikkori.community.api.adapter.out.email.EmailServicePortAdapter;
import borikkori.community.api.domain.user.repository.EmailVerificationRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationUsecaseImpl implements EmailVerificationUsecase {

    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailServicePort emailService;
    private final UserRepository  userRepository;
    private final UserMapper userMapper;
    @Override
    @Transactional
    public int sendEmail(String email) {
        int code = RandomCodeGenerator.generate();

        // 이메일 전송 (예외 처리는 EmailService 내부에서)
        emailService.send(email, code);

        // 이메일로 사용자 조회 (없으면 예외 발생)
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가진 사용자가 존재하지 않습니다."));

        // 도메인 User -> 영속성 모델(UserEntity)로 변환
        UserEntity userEntity = userMapper.toEntity(user);

        EmailVerificationEntity ev = EmailVerificationEntity.create(userEntity, code);

        // 이메일 인증 정보 저장
        emailVerificationRepository.saveVerification(ev);

        return code;
    }

    @Override
    @Transactional
    public boolean checkEmail(int verifyCode, String email) {
        EmailVerificationEntity ev = emailVerificationRepository.findLatestVerificationByEmail(email);
        if (verifyCode == ev.getVerificationNumber() && !ev.isSuccess()) {
            ev.setSuccess(true);
            return true;
        } else {
            throw new IllegalStateException("인증 번호가 일치하지 않거나 이미 인증된 상태입니다.");
        }
    }
}
