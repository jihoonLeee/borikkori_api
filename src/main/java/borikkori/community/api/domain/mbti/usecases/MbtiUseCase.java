package borikkori.community.api.domain.mbti.usecases;

import borikkori.community.api.domain.mbti.interfaces.dto.request.MbtiRequest;
import borikkori.community.api.domain.mbti.interfaces.dto.response.MbtiResultResponse;
import borikkori.community.api.domain.user.entities.User;

import java.util.List;

public interface MbtiUseCase {

    void addMbtiResult (MbtiRequest req);

    List<MbtiResultResponse> getMbtiTopX(int X);
}
