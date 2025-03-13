package borikkori.community.api.application.domain.mbti.usecase;

import borikkori.community.api.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiEntity;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiResultEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.domain.mbti.repository.MbtiRepository;
import borikkori.community.api.domain.user.repository.UserRepository;
import borikkori.community.api.adapter.in.web.mbti.request.MbtiRequest;
import borikkori.community.api.adapter.in.web.mbti.response.MbtiResultResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MbtiUseCaseImpl implements MbtiUseCase {

    private final MbtiRepository mbtiRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void addMbtiResult(MbtiRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new BadCredentialsException("잘못된 계정 정보"));

        MbtiEntity mbtiEntity = MbtiEntity.builder()
                .name(user != null ? user.getName() : "GUEST")
                .user(user)
                .result(req.getResult())
                .build();
        mbtiRepository.save(mbtiEntity);

        if (user != null) {
            user.setMbtiEntity(mbtiEntity);
        }

        MbtiResultEntity mbtiResultEntity = mbtiRepository.findByResult(req.getResult());
        mbtiResultEntity.setCount(mbtiResultEntity.getCount() + 1);
    }


    @Override
    public List<MbtiResultResponse> getMbtiTopX(int X) {
        List<MbtiResultResponse> topX = mbtiRepository.findTopX(X).stream()
                .map(mbti -> MbtiResultResponse.builder()
                        .type(mbti.getType())
                        .count(mbti.getCount())
                        .build())
                .collect(Collectors.toList());
        return topX;
    }
}
