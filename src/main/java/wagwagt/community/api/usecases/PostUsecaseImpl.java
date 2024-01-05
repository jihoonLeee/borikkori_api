package wagwagt.community.api.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wagwagt.community.api.entities.Post;
import wagwagt.community.api.repositories.PostRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostUsecaseImpl implements PostUsecase{

    private PostRepository postRepository;
    public Long posting(Post post){
        postRepository.save(post);
        return post.getId();
    }
}
