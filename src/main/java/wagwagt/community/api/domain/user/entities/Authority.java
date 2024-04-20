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
    @Column(unique = true)
    private Role role;

    @JsonIgnore
    @ManyToMany(mappedBy = "auth",fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();


    @Override
    public String getAuthority() {
        return this.role.getRole();
    }

}
