package wagwagt.community.api.common.exeptions;

import lombok.experimental.StandardException;

@StandardException
public class NotExistAuthException extends RuntimeException {
    public NotExistAuthException(String message) {
        super(message);
    }
}
