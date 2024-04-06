package wagwagt.community.api.domain.mbti.usecases;

import wagwagt.community.api.domain.mbti.interfaces.dto.request.MbtiRequest;
import wagwagt.community.api.domain.mbti.interfaces.dto.response.MbtiResultResponse;
import wagwagt.community.api.domain.user.entities.User;

import java.util.List;

public interface MbtiUseCase {

    void addMbtiResult (MbtiRequest req);

    List<MbtiResultResponse> getMbtiTopX(int X);
}
