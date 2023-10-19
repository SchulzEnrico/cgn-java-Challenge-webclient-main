package de.neuefische.cgnjava233webclient.character;

import de.neuefische.cgnjava233webclient.rickandmortyapi.RickAndMortyCharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharacterController {

	private final RickAndMortyCharacterService rickAndMortyCharacterService;

	@GetMapping("/characters")
	public List<Character> getAllCharacters(@RequestParam(required = false) String page){
		if(page != null){
			return rickAndMortyCharacterService.fetchAllCharacters(Integer.parseInt(page));
		}
		return rickAndMortyCharacterService.fetchAllCharacters();
	}

	//////////////////////////////
	// afternoon challenge
	@GetMapping({"/{id}"})
	public Character fetchCharacterById(@PathVariable String id) {
		return rickAndMortyCharacterService.fetchCharacterById(Integer.parseInt(id));
	}
}
