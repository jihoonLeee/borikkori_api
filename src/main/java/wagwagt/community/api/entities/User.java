package wagwagt.community.api.entities;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="user")
@Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name="user_name" ,nullable = false)
    private String name;

    @Column(name="user_passwd" ,nullable = false)
    private String password;

    @Column(name= "user_email",unique = true ,nullable = false)
    private String email;


    /**
     * TODO:권한 추가
     * */
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Authority> roles = new ArrayList<>();

    public void setRoles(List<Authority> role){
        this.roles=role;
        role.forEach(r -> r.setUser(this));
    }
    
//    private List<EmailVerification> emailVerifications = new ArrayList<>();
    
//    @OneToMany(mappedBy = "user")
//    private List<Feed> feeds = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<HospitalReview> hospitalReviews = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<Post> posts = new ArrayList<>();



}
