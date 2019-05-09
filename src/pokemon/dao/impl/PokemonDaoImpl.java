package pokemon.dao.impl;

import java.util.Collection;

import pokemon.dao.PokemonDao;
import pokemon.exceptions.PokemonNotFoundException;
import pokemon.file.PokemonFile;
import pokemon.model.Pokemon;

public class PokemonDaoImpl implements PokemonDao {
	
	private PokemonFile pkmFile = null;
	
    public PokemonDaoImpl(PokemonFile pkmFile) 
    {
    	this.pkmFile = pkmFile;
    }

	@Override
	public Pokemon findPokemon(String name) throws PokemonNotFoundException
	{
		Pokemon p = pkmFile.readPokemon(name.toLowerCase());
		
		if (p == null)
		{
			System.out.println("Could not find " + name + "!");
			throw new PokemonNotFoundException();
		}
		
		return p;
	}

	@Override
	public Collection<Pokemon> findAllPokemon()
	{
		return pkmFile.readAllPokemon();
	}

}