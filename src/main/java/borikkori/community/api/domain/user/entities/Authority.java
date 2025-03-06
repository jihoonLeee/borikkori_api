package borikkori.community.api.domain.user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import borikkori.community.api.domain.user.entities.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="authority")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority implements GrantedAuthority {
    public Authority(Role role) {
        this.role = role;
    }

    @Id
    @Column(name="authority_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private Role role;

    @Builder.Default
    @JsonIgnore
    @ManyToMany(mappedBy = "auth",fetch = FetchType.LAZY) //<< N+1 문제 발생 가능 / 지연로딩 사용..?
    private List<User> users = new ArrayList<>();


    @Override
    public String getAuthority() {
        return this.role.getRole();
    }

}
