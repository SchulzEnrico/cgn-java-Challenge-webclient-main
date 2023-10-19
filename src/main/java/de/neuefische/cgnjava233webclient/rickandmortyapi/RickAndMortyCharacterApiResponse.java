package de.neuefische.cgnjava233webclient.rickandmortyapi;

import de.neuefische.cgnjava233webclient.character.Character;
import java.util.List;

public record RickAndMortyCharacterApiResponse(
		List<Character> results ) {
}
