package borikkori.community.api.domain.mbti.repository;

import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiEntity;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiResultEntity;
import borikkori.community.api.common.enums.MbtiType;

import java.util.List;


public interface MbtiRepository {

    void save(MbtiEntity mbtiEntity);

    List<MbtiResultEntity> findTopX(int X);

    MbtiResultEntity findByResult(MbtiType type);

}
