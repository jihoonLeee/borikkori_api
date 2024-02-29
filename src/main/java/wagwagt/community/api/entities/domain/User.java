package wagwagt.community.api.entities.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="user")
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor(access = AccessLevel.PRIVATE)  // 모든 필드 값을 인자로 받는 생성자 추가
@Builder
@EntityListeners(AuditingEntityListener.class)
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

    @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="auth_id")
    private Authority auth;

    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updDate;

    public void setRole(Authority auth){
        this.auth=auth;
    }
    public void setPassword(String password){this.password=password;}
//    private List<EmailVerification> emailVerifications = new ArrayList<>();
    
//    @OneToMany(mappedBy = "user")
//    private List<Feed> feeds = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<HospitalReview> hospitalReviews = new ArrayList<>();
//

}
