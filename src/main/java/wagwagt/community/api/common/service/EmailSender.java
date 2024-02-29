package wagwagt.community.api.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;

    public void send(String to, int code){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ljh2723@gmail.com");
        message.setTo(to);
        message.setSubject("인증 메일 입니다.");
        message.setText("인증 코드 : " + code);
        mailSender.send(message);
    }
}
