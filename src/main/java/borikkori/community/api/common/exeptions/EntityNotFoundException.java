package borikkori.community.api.common.exeptions;

import lombok.experimental.StandardException;

@StandardException
public class EntityNotFoundException extends RuntimeException {
	public EntityNotFoundException(String message) {
		super(message);
	}
}
