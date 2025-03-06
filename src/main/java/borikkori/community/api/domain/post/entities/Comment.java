package borikkori.community.api.domain.post.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import borikkori.community.api.domain.user.entities.User;
import borikkori.community.api.common.enums.CommentStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="comment")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name="parent_comment_id")
    private Comment parentComment;

    private String contents;

    private CommentStatus commentStatus;

    private int likeCnt;

    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updDate;


    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }
}

