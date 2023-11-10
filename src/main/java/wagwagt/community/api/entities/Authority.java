package wagwagt.community.api.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import wagwagt.community.api.enums.Role;

@Entity
@Getter
@Builder
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Role role;

    @JoinColumn(name = "user")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    public void setUser(User user) {
        this.user = user;
    }
}
