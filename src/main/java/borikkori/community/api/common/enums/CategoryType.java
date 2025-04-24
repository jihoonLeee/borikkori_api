package borikkori.community.api.common.enums;

import lombok.Getter;

@Getter
public enum CategoryType {
	NOTICE("공지", "커뮤니티 공지사항을 전달합니다.", 5),
	FREE("자유", "자유로운 주제로 소통하는 공간입니다.", 2),
	INFO("정보공유", "반려견 관련 유용한 정보와 팁을 공유합니다.", 3),
	FUNNY("웃긴", "반려견의 웃긴 에피소드와 재미있는 이야기를 나눕니다.", 4),
	BEGINNER("초보 애견인", "반려견 초보자들을 위한 질문과 조언을 공유합니다.", 1);

	private final String displayName;
	private final String description;
	private final int displayOrder;

	CategoryType(String displayName, String description, int displayOrder) {
		this.displayName = displayName;
		this.description = description;
		this.displayOrder = displayOrder;
	}
}
