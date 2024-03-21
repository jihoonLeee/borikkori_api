package wagwagt.community.api.domain.mbti.usecases.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wagwagt.community.api.domain.mbti.entities.Mbti;
import wagwagt.community.api.domain.mbti.entities.MbtiResult;
import wagwagt.community.api.domain.user.entities.User;
import wagwagt.community.api.domain.mbti.interfaces.repositories.MbtiRepository;
import wagwagt.community.api.domain.user.interfaces.repositories.UserRepository;
import wagwagt.community.api.domain.mbti.interfaces.dto.request.MbtiRequest;
import wagwagt.community.api.domain.mbti.interfaces.dto.response.MbtiResultResponse;
import wagwagt.community.api.domain.mbti.usecases.MbtiUseCase;

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
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        Mbti mbti = Mbti.builder()
                        .name(user.isPresent()?user.get().getName():"GUEST")
                        .user(user.isPresent()?user.get():null)
                        .result(req.getResult()).build();
        mbtiRepository.save(mbti);
        MbtiResult mbtiResult = mbtiRepository.findByResult(req.getResult());
        mbtiResult.setCount(mbtiResult.getCount()+1);
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
