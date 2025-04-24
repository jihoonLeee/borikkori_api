package borikkori.community.api.application.port;

public interface EmailServicePort {
	void send(String to, int code);
}
