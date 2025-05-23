package borikkori.community.api.adapter.out.persistence.user.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.out.persistence.user.mapper.UserMapper;
import borikkori.community.api.adapter.out.persistence.user.repository.SpringDataUserJpaRepository;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

	private final SpringDataUserJpaRepository userJpaRepository;
	private final UserMapper userMapper;

	@Override
	public User saveUser(User user) {
		UserEntity userEntity = userMapper.toEntity(user);
		UserEntity savedUserEntity = userJpaRepository.save(userEntity);
		return userMapper.toDomain(savedUserEntity);
	}

	@Override
	public List<User> findAll() {
		List<UserEntity> userEntities = userJpaRepository.findAll();
		return userEntities.stream()
			.map(userMapper::toDomain)
			.collect(Collectors.toList());
	}

	@Override
	public Optional<User> findById(Long id) {
		return userJpaRepository.findById(id)
			.map(userMapper::toDomain);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userJpaRepository.findWithRolesByEmail(email)
			.map(userMapper::toDomain);
	}

	@Override
	public void delete(User user) {
		userJpaRepository.deleteById(user.getId().getId());
	}

}
