package softeer.h9.hey.controller.car;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import softeer.h9.hey.dto.car.request.SelectOptionNormalRequest;
import softeer.h9.hey.dto.car.response.HGenuineAccessoriesResponse;
import softeer.h9.hey.dto.car.response.SelectOptionByModelIdResponse;
import softeer.h9.hey.dto.global.response.GlobalResponse;
import softeer.h9.hey.dto.car.request.SelectOptionRequest;
import softeer.h9.hey.service.car.SelectOptionService;
import softeer.h9.hey.dto.car.response.SelectOptionsResponse;

@RestController
@RequiredArgsConstructor
public class SelectOptionController {
	private final SelectOptionService selectOptionService;

	@GetMapping("/car/select-options")
	public GlobalResponse<SelectOptionByModelIdResponse> getSelectOptionsByModelId(
		SelectOptionNormalRequest selectOptionNormalRequest) {
		SelectOptionByModelIdResponse response = selectOptionService.findAllSelectOptionByModelId(selectOptionNormalRequest);
		return GlobalResponse.ok(response);
	}

	@GetMapping("/car/select-option")
	public GlobalResponse<SelectOptionsResponse> getAvailableSelectOptions(SelectOptionRequest selectOptionRequest) {
		SelectOptionsResponse selectOptionsResponse
			= selectOptionService.findSelectOption(selectOptionRequest);
		return GlobalResponse.ok(selectOptionsResponse);
	}

	@GetMapping("/car/n-performance")
	public GlobalResponse<SelectOptionsResponse> getAvailableNPerformanceOptions(
		SelectOptionRequest selectOptionRequest) {
		SelectOptionsResponse nPerformanceOptionsResponse
			= selectOptionService.findNPerformanceOption(selectOptionRequest);
		return GlobalResponse.ok(nPerformanceOptionsResponse);
	}

	@GetMapping("/car/h-genuine-accessories")
	public GlobalResponse<HGenuineAccessoriesResponse> getAvailableHGenuineOption(
		@Valid final SelectOptionRequest selectOptionRequest) {
		HGenuineAccessoriesResponse hGenuineAccessoriesResponse = selectOptionService.findHGenuineOption(
			selectOptionRequest);
		return GlobalResponse.ok(hGenuineAccessoriesResponse);
	}
}
