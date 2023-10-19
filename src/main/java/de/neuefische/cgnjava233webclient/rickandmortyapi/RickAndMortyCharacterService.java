package de.neuefische.cgnjava233webclient.rickandmortyapi;

import de.neuefische.cgnjava233webclient.character.Character;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
public class RickAndMortyCharacterService {
	private final WebClient webClient;

	public RickAndMortyCharacterService(@Value("${rickandmorty.characters.baseUrl}") String baseUrl){
		this.webClient = WebClient.builder().baseUrl(baseUrl).build();
	}

	public List<Character> fetchAllCharacters(int page) {
		if (page < 1) {
			throw new IllegalArgumentException("Page must be greater than zero.");
		}
		RickAndMortyCharacterApiResponse response = Objects.requireNonNull(webClient
						.get()
						.uri(uriBuilder -> uriBuilder
								.queryParam("page", page)
								.build())
						.retrieve()
						.toEntity(RickAndMortyCharacterApiResponse.class)
						.block())
				.getBody();
		assert response != null;
		return response.results();
	}

	public List<Character> fetchAllCharacters() {
		RickAndMortyCharacterApiResponse response = Objects.requireNonNull(webClient
						.get()
						.retrieve()
						.toEntity(RickAndMortyCharacterApiResponse.class)
						.block())
				.getBody();
		assert response != null;
		return response.results();
	}

	//////////////////////////////
	// afternoon challenge
	public Character fetchCharacterById(int id) {
		Character response = Objects.requireNonNull(webClient
						.get()
						.uri("/{id}", id)
						.retrieve()
						.toEntity(Character.class)
						.block())
				.getBody();
		System.out.println(response);
		assert response != null;
		return response;
	}

	public static void main(String[] args) {
		RickAndMortyCharacterService rickAndMortyCharacterService = new RickAndMortyCharacterService("https://rickandmortyapi.com/api/character");
		System.out.println(rickAndMortyCharacterService.fetchCharacterById(1));
	}
}
