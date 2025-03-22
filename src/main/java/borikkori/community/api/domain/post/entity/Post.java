package borikkori.community.api.domain.post.entity;

import borikkori.community.api.common.enums.PostStatus;
import borikkori.community.api.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Post {
    private final Long id;
    private final User user;
    private String title;
    private String contents;
    private int visitCount;
    private int likeCount;
    private PostStatus postStatus;
    private final LocalDateTime regDate;
    private LocalDateTime updDate;

    public void updateContent(String title, String contents) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title must not be empty");
        }
        if (contents == null || contents.isEmpty()) {
            throw new IllegalArgumentException("Contents must not be empty");
        }
        this.title = title;
        this.contents = contents;
        updateModificationDate();
    }
    public void incrementVisit() {
        this.visitCount++;
        updateModificationDate();
    }
    public void addLike() {
        this.likeCount++;
        updateModificationDate();
    }
    public void updatePostStatus(PostStatus status) {
        this.postStatus = status;
        updateModificationDate();
    }
    private void updateModificationDate() {
        this.updDate = LocalDateTime.now();
    }
}
