package pokemon.dao;

import java.util.Collection;

import pokemon.exceptions.PokemonNotFoundException;
import pokemon.model.Pokemon;

public interface PokemonDao {

	Pokemon findPokemon(String name) throws PokemonNotFoundException;
	
	Collection<Pokemon> findAllPokemon();
}
