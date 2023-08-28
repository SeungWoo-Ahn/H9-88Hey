package softeer.h9.hey.controller.car;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import softeer.h9.hey.dto.car.request.TagByExteriorColorIdRequest;
import softeer.h9.hey.dto.car.request.TagByInteriorColorIdRequest;
import softeer.h9.hey.dto.car.request.TagBySelectOptionRequest;
import softeer.h9.hey.dto.car.response.TagResponse;
import softeer.h9.hey.dto.global.response.GlobalResponse;
import softeer.h9.hey.service.car.TagService;

@RestController
@RequiredArgsConstructor
public class TagController {

	private static final int DEFAULT_TOP_NUMBER = 5;

	private final TagService tagService;

	@GetMapping("/car/tag/select-option")
	public GlobalResponse<TagResponse> getTopTagBySelectOption(@Valid TagBySelectOptionRequest request) {
		if (request.getLimit() == null) {
			request.setLimit(DEFAULT_TOP_NUMBER);
		}
		return GlobalResponse.ok(tagService.findTopBySelectOptionId(request));
	}

	@GetMapping("/car/tag/exterior-color")
	public GlobalResponse<TagResponse> getTopTagByExteriorColor(@Valid TagByExteriorColorIdRequest request) {
		if (request.getLimit() == null) {
			request.setLimit(DEFAULT_TOP_NUMBER);
		}
		return GlobalResponse.ok(tagService.findTopByExteriorColorId(request));
	}

	@GetMapping("/car/tag/interior-color")
	public GlobalResponse<TagResponse> getTopTagByInteriorColor(@Valid TagByInteriorColorIdRequest request) {
		if (request.getLimit() == null) {
			request.setLimit(DEFAULT_TOP_NUMBER);
		}
		return GlobalResponse.ok(tagService.findTopByInteriorColorId(request));
	}
}
