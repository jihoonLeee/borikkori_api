package borikkori.community.api.common.exeptions;

import lombok.experimental.StandardException;

@StandardException
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
