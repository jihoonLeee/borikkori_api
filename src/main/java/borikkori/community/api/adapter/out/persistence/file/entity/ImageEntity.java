package borikkori.community.api.adapter.out.persistence.file.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.common.enums.ImageStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name="image")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ImageEntity {

    @Id @Column(name="image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    private String originalName;

    private String extension;

    private String savedName;

    private String savedUrl;

    @Enumerated(EnumType.STRING)
    private ImageStatus imageStatus;

    private long imageSize;

    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime regDate;


    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updDate;

    public void setImageStatus(ImageStatus imageStatus) {
        this.imageStatus = imageStatus;
    }
}
