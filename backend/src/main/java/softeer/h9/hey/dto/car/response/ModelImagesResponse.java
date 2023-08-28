package softeer.h9.hey.dto.car.response;

import java.util.List;

import lombok.Getter;

@Getter
public class ModelImagesResponse {
	private final List<String> carImageUrls;

	private ModelImagesResponse(final List<String> imageUrls) {
		this.carImageUrls = imageUrls;
	}

	public static ModelImagesResponse of(final List<String> imageUrls) {
		return new ModelImagesResponse(imageUrls);
	}
}
