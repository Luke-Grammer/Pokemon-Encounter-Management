package pokemonDB.dao;

import java.util.Collection;

import pokemonDB.model.Pokemon;

public interface PokemonDao {

	Pokemon findPokemon(String name);
	
	Collection<Pokemon> findAllPokemon();
}
