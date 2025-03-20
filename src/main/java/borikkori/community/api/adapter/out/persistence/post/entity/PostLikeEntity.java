package borikkori.community.api.adapter.out.persistence.post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="post_like")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PostLikeEntity {

    @EmbeddedId
    private PostLikeIdEntity id;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime regDate;
}

