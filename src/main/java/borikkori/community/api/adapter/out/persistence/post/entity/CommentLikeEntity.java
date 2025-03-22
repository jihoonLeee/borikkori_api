package borikkori.community.api.adapter.out.persistence.post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="comment_like")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CommentLikeEntity {
    @EmbeddedId
    private CommentLikeIdEntity id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", insertable = false, updatable = false)
    private CommentEntity commentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime regDate;
}

