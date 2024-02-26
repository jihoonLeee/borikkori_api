package wagwagt.community.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wagwagt.community.api.enums.Role;

import java.util.ArrayList;
import java.util.List;

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
