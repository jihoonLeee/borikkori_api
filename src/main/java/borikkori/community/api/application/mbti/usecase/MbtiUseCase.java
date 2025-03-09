package borikkori.community.api.application.mbti.usecase;

import borikkori.community.api.adapter.in.web.mbti.request.MbtiRequest;
import borikkori.community.api.adapter.in.web.mbti.response.MbtiResultResponse;

import java.util.List;

public interface MbtiUseCase {

    void addMbtiResult (MbtiRequest req);

    List<MbtiResultResponse> getMbtiTopX(int X);
}
