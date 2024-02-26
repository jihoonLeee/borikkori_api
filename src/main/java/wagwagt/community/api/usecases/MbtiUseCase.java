package wagwagt.community.api.usecases;

import wagwagt.community.api.entities.MbtiResult;
import wagwagt.community.api.requests.MbtiRequest;
import wagwagt.community.api.responses.MbtiResultResponse;

import java.util.List;

public interface MbtiUseCase {

    void addMbtiResult(MbtiRequest req);

    List<MbtiResultResponse> getMbtiTopX(int X);
}
