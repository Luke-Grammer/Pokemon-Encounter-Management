package pokemon.dao;

import java.util.Collection;

import pokemon.model.Pokemon;

public interface PokemonDao {

	Pokemon findPokemon(String name);
	
	Collection<Pokemon> findAllPokemon();
}
