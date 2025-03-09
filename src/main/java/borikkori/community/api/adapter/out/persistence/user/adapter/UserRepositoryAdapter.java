package borikkori.community.api.adapter.out.persistence.user.adapter;

import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.out.persistence.user.repository.SpringDataUserJpaRepository;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final SpringDataUserJpaRepository userJpaRepository;

    @Override
    public void save(User user) {

    }

    @Override
    public List<UserEntity> findAll() {
        return List.of();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void delete(User user) {

    }
}
