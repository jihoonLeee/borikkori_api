package wagwagt.community.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wagwagt.community.api.enums.Role;

@Entity
@Getter
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor  // 모든 필드 값을 인자로 받는 생성자 추가
@Builder
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Role role;

    @JsonIgnore
    @JoinColumn(name = "user")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    public void setUser(User user) {
        this.user = user;
    }
}
