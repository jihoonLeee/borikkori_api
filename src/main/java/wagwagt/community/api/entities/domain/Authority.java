package wagwagt.community.api.entities.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wagwagt.community.api.entities.domain.enums.Role;

@Entity
@Getter
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor  // 모든 필드 값을 인자로 받는 생성자 추가
@Builder
public class Authority {

    @Id
    @Column(name="auth_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

//    @JsonIgnore
//    @OneToMany(mappedBy = "auth", fetch = FetchType.LAZY)
//    private List<User> users = new ArrayList<>();
//
//    //...
//
//    public void addUser(User user) {
//        this.users.add(user);
//        user.setAuth(this);
//    }
}
