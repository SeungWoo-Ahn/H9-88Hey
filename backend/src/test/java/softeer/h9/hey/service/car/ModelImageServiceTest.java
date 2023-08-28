package softeer.h9.hey.service.car;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import softeer.h9.hey.dto.car.response.ModelImagesResponse;

@SpringBootTest
@DisplayName("ModelImageService Test")
class ModelImageServiceTest {

	@Autowired
	private ModelImageService modelImageService;

	@Test
	@DisplayName("모델 아이디와 함께 모델 이미지를 요청하면 ModelImagesResponse 객체에 값을 담아 반환해야 한다.")
	void findModelImages() {
		int modelId = 1;

		ModelImagesResponse modelImagesResponse = modelImageService.findModelImageUrlsByModelId(modelId);
		List<String> imageUrls = modelImagesResponse.getCarImageUrls();

		Assertions.assertThat(imageUrls).hasSize(4);
	}

}
