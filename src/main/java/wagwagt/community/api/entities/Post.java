package wagwagt.community.api.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name="post")
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String contents;

    private int visitCnt;

    private int likeCount;

    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime regDate;


    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updDate;


}
