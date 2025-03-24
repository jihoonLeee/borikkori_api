package borikkori.community.api.application.domain.mbti.usecase;

import java.util.List;

import borikkori.community.api.adapter.in.web.mbti.request.MbtiRequest;
import borikkori.community.api.adapter.in.web.mbti.response.MbtiResultResponse;

public interface MbtiUseCase {

	void addMbtiResult(MbtiRequest req);

	List<MbtiResultResponse> getMbtiTopX(int X);
}
