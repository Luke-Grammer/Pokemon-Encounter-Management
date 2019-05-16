package pokemon.dao.impl;

import java.util.Collection;

import pokemon.dao.PokemonDao;
import pokemon.file.PokemonFile;
import pokemon.model.Pokemon;
import pokemon.model.impl.PokemonImpl;

public class PokemonDaoImpl implements PokemonDao {
	
	private PokemonFile pkmFile = null;
	
    public PokemonDaoImpl(PokemonFile pkmFile) 
    {
    	this.pkmFile = pkmFile;
    }

	@Override
	public Pokemon findPokemon(String name)
	{
		name = name.trim().toLowerCase();
		
		if (name.contains("missingno"))
			return new PokemonImpl(0, "MissingNo.", "MissingNo.", "Have you talked to the old man yet?", "NORMAL", "FLYING");
		
		Pokemon p = pkmFile.readPokemon(name);
		
		if (p == null && name.length() > 0)
			System.out.println("Could not find " + name + "!\n");
		else if (p == null)
			System.out.println("Pokemon not found!\n");
		
		return p;
	}

	@Override
	public Collection<Pokemon> findAllPokemon()
	{
		return pkmFile.readAllPokemon();
	}

}