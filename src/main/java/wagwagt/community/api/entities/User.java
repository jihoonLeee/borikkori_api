package wagwagt.community.api.entities;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

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

    @Column(name="user_name")
    private String name;

    @Column(name="user_passwd")
    private String password;

    @Column(name= "user_email",unique = true)
    private String email;

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
