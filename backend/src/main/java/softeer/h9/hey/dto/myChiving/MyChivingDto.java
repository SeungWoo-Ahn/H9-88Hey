package softeer.h9.hey.dto.myChiving;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import softeer.h9.hey.dto.archiving.BodyTypeDto;
import softeer.h9.hey.dto.archiving.EngineDto;
import softeer.h9.hey.dto.archiving.ExteriorColorDto;
import softeer.h9.hey.dto.archiving.InteriorColorDto;
import softeer.h9.hey.dto.archiving.TrimDto;
import softeer.h9.hey.dto.archiving.WheelDriveDto;

@Getter
@Setter
@Builder
public class MyChivingDto {
	private Long myChivingId;
	private LocalDateTime lastModifiedDate;
	private Boolean isSaved;
	private ModelDto model;
	private TrimDto trim;
	private EngineDto engine;
	private BodyTypeDto bodyType;
	private WheelDriveDto wheelDrive;
	private InteriorColorDto interiorColor;
	private ExteriorColorDto exteriorColor;

	public int getTotalPrice() {
		int totalPrice = 0;

		if (trim != null) {
			totalPrice += trim.getPrice();
		}
		if (engine != null) {
			totalPrice += engine.getAdditionalPrice();
		}
		if (bodyType != null) {
			totalPrice += bodyType.getAdditionalPrice();
		}
		if (wheelDrive != null) {
			totalPrice += wheelDrive.getAdditionalPrice();
		}
		if (exteriorColor != null) {
			totalPrice += exteriorColor.getAdditionalPrice();
		}

		return totalPrice;
	}

}
