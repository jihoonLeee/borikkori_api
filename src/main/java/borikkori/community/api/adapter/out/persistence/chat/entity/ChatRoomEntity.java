package borikkori.community.api.adapter.out.persistence.chat.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import borikkori.community.api.common.enums.ActiveStatus;
import borikkori.community.api.common.enums.ChatRoomType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "chat_room")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ChatRoomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chat_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private ChatRoomType type;

	@Enumerated(EnumType.STRING)
	private ActiveStatus status;

	@Column(unique = true)
	private String name;

	private int maxUsers = 50;

	private int currentUserCount = 0;

	@CreatedDate
	@Column(updatable = false, nullable = false)
	private LocalDateTime regDate;

	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime updDate;

	// 참여자 수 증가 메소드
	public void userEnter() {
		if (this.currentUserCount < this.maxUsers) {
			this.currentUserCount++;
		} else {
			throw new IllegalStateException("채팅방이 가득찼습니다.");
		}
	}

	// 참여자 수 감소 메소드
	public void userExit() {
		if (this.currentUserCount > 0) {
			this.currentUserCount--;
		} else {
			throw new IllegalStateException("현재 0명입니다.");
		}
	}
}
