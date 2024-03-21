package wagwagt.community.api.domain.user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import wagwagt.community.api.domain.user.entities.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority implements GrantedAuthority {

    @Id
    @Column(name="auth_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "auth", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();


//    public void addUser(User user) {
//        this.users.add(user);
//        user.setAuth(this);
//    }

    @Override
    public String getAuthority() {
        return this.role.getRole();
    }

}
