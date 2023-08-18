package softeer.h9.hey.controller.myChiving;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class MyChivingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("최종 저 시에 해당 저장건에 대한 마이카이빙 id를 반환해야 한다")
	void saveMyCarToMyChiving() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/mychiving")
				.contentType("application/json")
				.content("{\n"
					+ "    \"myChivingId\": 480412842277874797,\n"
					+ "\t\"bodyType\": 1,\n"
					+ "\t\"wheelType\": 2,\n"
					+ "\t\"engine\": 2,\n"
					+ "\t\"trim\": 2,\n"
					+ "\t\"exteriorColor\": 3,\n"
					+ "\t\"interiorColor\": 5,\n"
					+ "\t\"selectOptions\":[\n"
					+ "\t\t\"DUP\", \"LX0010\", \"PA1\"\n"
					+ "\t]\n"
					+ "}"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.myChivingId").value(480412842277874797L));
	}

	@Test
	@DisplayName("")
	void temporarySaveMyCarToMyChiving() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/mychiving/temp")
				.contentType("application/json")
				.content("{\n"
					+ "    \"myChivingId\": null,\n"
					+ "\t\"bodyType\": 1,\n"
					+ "\t\"wheelType\": 2,\n"
					+ "\t\"engine\": 2,\n"
					+ "\t\"trim\": 2,\n"
					+ "\t\"exteriorColor\": 3,\n"
					+ "\t\"interiorColor\": 5,\n"
					+ "\t\"selectOptions\":[\n"
					+ "\t\t\"DUP\", \"LX0010\", \"PA1\"\n"
					+ "\t]\n"
					+ "}"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.myChivingId").exists());
	}
}
