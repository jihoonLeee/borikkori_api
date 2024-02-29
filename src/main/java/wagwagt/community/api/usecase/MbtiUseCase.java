package wagwagt.community.api.usecase;

import wagwagt.community.api.interfaces.controller.dto.requests.MbtiRequest;
import wagwagt.community.api.interfaces.controller.dto.responses.MbtiResultResponse;

import java.util.List;

public interface MbtiUseCase {

    void addMbtiResult(MbtiRequest req);

    List<MbtiResultResponse> getMbtiTopX(int X);
}
