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
		name = name.trim().toLowerCase();
		
		if (name.contains("missingno"))
			return new Pokemon(0, "MissingNo.", "MissingNo.", "Have you talked to the old man yet?", "NORMAL", "FLYING");
		
		Pokemon p = pkmFile.readPokemon(name);
		
		if (p == null && name.length() > 0)
		{
			System.out.println("Could not find " + name + "!\n");
			throw new PokemonNotFoundException();
		}
		else if (p == null)
		{
			System.out.println("Pokemon not found!\n");
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