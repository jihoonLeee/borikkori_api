package wagwagt.community.api.interfaces.controller.repositories;

import wagwagt.community.api.entities.domain.Mbti;
import wagwagt.community.api.entities.domain.MbtiResult;
import wagwagt.community.api.entities.domain.enums.MbtiType;

import java.util.List;


public interface MbtiRepository {


    void save(Mbti mbti);

    List<MbtiResult> findTopX(int X);

    MbtiResult findByResult(MbtiType type);

}
