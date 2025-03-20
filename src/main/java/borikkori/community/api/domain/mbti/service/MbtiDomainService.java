package borikkori.community.api.domain.mbti.service;

import borikkori.community.api.common.enums.MbtiType;
import borikkori.community.api.domain.mbti.entity.MbtiStatistics;
import borikkori.community.api.domain.mbti.entity.UserMbti;

public interface MbtiDomainService {
    UserMbti createUserMbti(MbtiType result, String testName);
    MbtiStatistics updateStatistics(MbtiType type, MbtiStatistics currentStats);
}
