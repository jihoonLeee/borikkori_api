package borikkori.community.api.application.domain.mbti.usecase;

import borikkori.community.api.adapter.out.persistence.mbti.mapper.MbtiMapper;
import borikkori.community.api.adapter.out.persistence.mbti.mapper.MbtiStatisticsMapper;
import borikkori.community.api.domain.mbti.entity.MbtiStatistics;
import borikkori.community.api.domain.mbti.entity.UserMbti;
import borikkori.community.api.domain.mbti.service.MbtiDomainService;
import borikkori.community.api.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiEntity;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiResultEntity;
import borikkori.community.api.domain.mbti.repository.MbtiRepository;
import borikkori.community.api.domain.user.repository.UserRepository;
import borikkori.community.api.adapter.in.web.mbti.request.MbtiRequest;
import borikkori.community.api.adapter.in.web.mbti.response.MbtiResultResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MbtiUseCaseImpl implements MbtiUseCase {

    private final MbtiRepository mbtiRepository;
    private final UserRepository userRepository;
    private final MbtiDomainService mbtiDomainService;
    private final MbtiMapper mbtiMapper;
    private final MbtiStatisticsMapper mbtiStatisticsMapper;

    @Transactional
    @Override
    public void addMbtiResult(MbtiRequest req) {
        // 1. 사용자 조회 (없으면 예외 발생)
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new BadCredentialsException("잘못된 계정 정보"));

        // 2. 도메인 객체 생성: 사용자 MBTI 결과 생성
        UserMbti userMbti = mbtiDomainService.createUserMbti(req.getResult(), user.getName());

        // 3. 도메인 객체를 영속성 엔티티로 변환 및 저장
        mbtiRepository.saveUserMbti(userMbti);

        // 4. MBTI 통계 업데이트:
        //    - 먼저, 현재 결과에 해당하는 통계 엔티티를 조회 (한 번 호출하여 재사용)
        MbtiStatistics mbtiStatistics = mbtiRepository.findByResult(req.getResult());

        //    - 도메인 서비스에 통계 업데이트 로직을 위임
        MbtiResultEntity mbtiResultEntity = mbtiStatisticsMapper.toEntity(mbtiStatistics);
        MbtiStatistics updatedStats = mbtiDomainService.updateStatistics(req.getResult(), mbtiStatistics);
        mbtiResultEntity.setCount(updatedStats.getCount());
    }

    @Override
    public List<MbtiResultResponse> getMbtiTopX(int topX) {
        return mbtiRepository.findTopX(topX).stream()
                .map(mbtiStatisticsMapper::toResponse)
                .collect(Collectors.toList());
    }
}
