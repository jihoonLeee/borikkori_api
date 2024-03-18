package wagwagt.community.api.entities.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import wagwagt.community.api.entities.domain.enums.PostStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name="post")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    private int likeCnt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Image> images;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updDate;

    public void setVisitCnt(int visitCnt) {
        this.visitCnt = visitCnt;
    }

    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }
}
