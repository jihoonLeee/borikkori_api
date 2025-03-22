package borikkori.community.api.domain.mbti.repository;

import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiEntity;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiResultEntity;
import borikkori.community.api.common.enums.MbtiType;
import borikkori.community.api.common.enums.Role;
import borikkori.community.api.domain.mbti.entity.MbtiStatistics;
import borikkori.community.api.domain.mbti.entity.UserMbti;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface MbtiRepository {

    void saveUserMbti(UserMbti userMbti);
    void saveMbtiResult(MbtiStatistics mbtiStatistics);
    Page<MbtiStatistics> findTopX(int X);

    MbtiStatistics findByResult(MbtiType type);

    long countMbtiResult() ;
}
