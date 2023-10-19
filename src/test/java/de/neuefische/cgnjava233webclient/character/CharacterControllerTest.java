package de.neuefische.cgnjava233webclient.character;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.cgnjava233webclient.rickandmortyapi.RickAndMortyCharacterApiResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class CharacterControllerTest {
	private static MockWebServer mockWebServer;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeAll
	static void init() throws IOException {
		mockWebServer = new MockWebServer();
		mockWebServer.start();
	}

	@AfterAll
	static void tearDown() throws IOException{
		mockWebServer.shutdown();
	}

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry){
		registry.add("rickandmorty.characters.baseUrl", () -> mockWebServer.url("/").toString());
	}

	@Test
	void getAllCharacters() throws Exception {
		List<Character> characters = List.of(
				new Character(1, "test", "test", "test", "test"),
				new Character(2, "test2", "test2", "test2", "test2"),
				new Character(3, "test3", "test3", "test3", "test3")
		);
		String charactersAsJson = objectMapper.writeValueAsString(characters);

		MockResponse response = new MockResponse();
		response.setBody(objectMapper.writeValueAsString(new RickAndMortyCharacterApiResponse(characters)));
		response.addHeader("Content-Type", "application/json");

		mockWebServer.enqueue(response);

		mockMvc.perform(get("/api/characters"))
				.andExpect(status().isOk())
				.andExpect(content().json(charactersAsJson));
	}

	//////////////////////////////
	// afternoon challenge
	@Test
	void fetchCharacterById() throws Exception {
		Character character = new Character(1, "test", "test", "test", "test");

		String characterAsJson = objectMapper.writeValueAsString(character);

		MockResponse response = new MockResponse();
		response.setBody(characterAsJson);
		response.addHeader("Content-Type", "application/json");

		mockWebServer.enqueue(response);

		mockMvc.perform(get("/api/characters/1"))
				.andExpect(status().isOk())
				.andExpect(content().json(characterAsJson));
	}
}