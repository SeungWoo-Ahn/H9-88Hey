package softeer.h9.hey.dto.car.response;

import java.util.List;

import lombok.Getter;

@Getter
public class MyChivingIdResponse {
	private final Long myChivingId;

	private MyChivingIdResponse(final Long myChivingId) {
		this.myChivingId = myChivingId;
	}

	public static MyChivingIdResponse of(final Long myChivingId) {
		return new MyChivingIdResponse(myChivingId);
	}
}
