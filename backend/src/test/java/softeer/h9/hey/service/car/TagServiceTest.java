package softeer.h9.hey.service.car;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import softeer.h9.hey.dto.car.request.TagByExteriorColorIdRequest;
import softeer.h9.hey.dto.car.request.TagByInteriorColorIdRequest;
import softeer.h9.hey.dto.car.request.TagBySelectOptionRequest;
import softeer.h9.hey.dto.car.response.TagResponse;
import softeer.h9.hey.repository.car.TagRepository;

class TagServiceTest {

	private final TagRepository tagRepository = Mockito.mock(TagRepository.class);

	private final TagService tagService = new TagService(tagRepository);

	@DisplayName("선택옵션 id에 따라 상위 limit개의 태그들을 조회한다.")
	@Test
	void findTopBySelectOptionId() {
		String id = "HSS";
		int limit = 3;
		TagBySelectOptionRequest request = TagBySelectOptionRequest.of(id, limit);

		List<String> targetTags = List.of("펠리세이드 전용",
			"반려동물🐱",
			"관리하기 편해요🧹");
		when(tagRepository.findTopBySelectOptionId(id, limit)).thenReturn(targetTags);

		TagResponse tagResponse = tagService.findTopBySelectOptionId(request);
		List<String> tags = tagResponse.getTags();

		assertTrue(limit >= tags.size());
	}

	@DisplayName("외장색상 id에 따라 상위 limit개의 태그들을 조회한다.")
	@Test
	void findTopByExteriorColorId() {
		int id = 1;
		int limit = 3;
		TagByExteriorColorIdRequest request = TagByExteriorColorIdRequest.of(id, limit);

		List<String> targetTags = List.of("멋있어요",
			"세련된 디자인",
			"트렌디해요");
		when(tagRepository.findTopByExteriorColorId(id, limit)).thenReturn(targetTags);

		TagResponse tagResponse = tagService.findTopByExteriorColorId(request);
		List<String> tags = tagResponse.getTags();

		assertTrue(limit >= tags.size());
	}

	@DisplayName("내장색상 id에 따라 상위 limit개의 태그들을 조회한다.")
	@Test
	void findTopByInteriorColorId() {
		int id = 1;
		int limit = 3;
		TagByInteriorColorIdRequest request = TagByInteriorColorIdRequest.of(id, limit);

		List<String> targetTags = List.of("멋있어요",
			"세련된 디자인",
			"트렌디해요");
		when(tagRepository.findTopByInteriorColorId(id, limit)).thenReturn(targetTags);

		TagResponse tagResponse = tagService.findTopByInteriorColorId(request);
		List<String> tags = tagResponse.getTags();

		assertTrue(limit >= tags.size());
	}
}
