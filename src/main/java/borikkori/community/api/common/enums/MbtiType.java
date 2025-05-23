package borikkori.community.api.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MbtiType {
	INFJ, INFP, INTJ, INTP,
	ISFJ, ISFP, ISTJ, ISTP,

	ENFJ, ENFP, ENTJ, ENTP,
	ESFJ, ESFP, ESTJ, ESTP;

	@JsonValue
	public String getType() {
		return this.toString();
	}

}
