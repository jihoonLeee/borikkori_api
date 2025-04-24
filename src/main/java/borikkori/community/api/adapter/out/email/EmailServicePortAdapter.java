package borikkori.community.api.adapter.out.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import borikkori.community.api.application.port.EmailServicePort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServicePortAdapter implements EmailServicePort {

	private final JavaMailSender mailSender;

	@Override
	public void send(String to, int code) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("ljh2723@gmail.com");
		message.setTo(to);
		message.setSubject("인증 메일 입니다.");
		message.setText("인증 코드 : " + code);
		mailSender.send(message);
	}
}
