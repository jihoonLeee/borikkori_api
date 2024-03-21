package wagwagt.community.api.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CommentStatus {
    OPEN("OPEN"),
    HIDE("HIDE"),
    DELETE("DELETE");

    private String status;

    CommentStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}
