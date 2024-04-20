package wagwagt.community.api.domain.user.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {

    USER("USER"),
    ADMIN("ADMIN"),

    MBTI_ISTJ("MBTI_ISTJ"),
    MBTI_ISFJ("MBTI_ISFJ"),
    MBTI_INFJ("MBTI_INFJ"),
    MBTI_INTJ("MBTI_INTJ"),
    MBTI_ISTP("MBTI_ISTP"),
    MBTI_ISFP("MBTI_ISFP"),
    MBTI_INFP("MBTI_INFP"),
    MBTI_INTP("MBTI_INTP"),
    MBTI_ESTP("MBTI_ESTP"),
    MBTI_ESFP("MBTI_ESFP"),
    MBTI_ENFP("MBTI_ENFP"),
    MBTI_ENTP("MBTI_ENTP"),
    MBTI_ESTJ("MBTI_ESTJ"),
    MBTI_ESFJ("MBTI_ESFJ"),
    MBTI_ENFJ("MBTI_ENFJ"),
    MBTI_ENTJ("MBTI_ENTJ");
    private String role;

    Role(String role) {
        this.role = role;
    }

    @JsonValue
    public String getRole() {
        return role;
    }

    @JsonCreator
    public static Role fromString(String role) {
        for (Role r : Role.values()) {
            if (r.role.equalsIgnoreCase(role)) {
                return r;
            }
        }
        throw new IllegalArgumentException("유효 하지 않은 권한: " + role);
    }
}
