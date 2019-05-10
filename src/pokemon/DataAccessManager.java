package pokemon;

import java.util.Collection;

import pokemon.dao.impl.PartyDaoImpl;
import pokemon.dao.impl.PokemonDaoImpl;
import pokemon.exceptions.PartyOverflowException;
import pokemon.exceptions.PokemonNotFoundException;
import pokemon.file.PokemonFile;
import pokemon.model.Party;
import pokemon.model.Pokemon;

public class PokemonManager {
	private static final String FILENAME = "pokemon.csv";
	private static final String PARTY_FILENAME = "party.csv";

	private PokemonDaoImpl pkmDao = null;
	private PartyDaoImpl partyDao = null;
	private PokemonFile pkmFile = null;
	private PokemonFile partyFile = null;

	public PokemonManager() 
	{
		pkmFile = new PokemonFile(FILENAME);
		pkmDao = new PokemonDaoImpl(pkmFile);

		partyFile = new PokemonFile(PARTY_FILENAME);
		partyDao = new PartyDaoImpl(pkmDao, partyFile); // TODO: Implement multiple parties?
	}

	public Pokemon findPokemon(String name)
	{
		try
		{
			return pkmDao.findPokemon(name);
		} catch (PokemonNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}

	public Collection<Pokemon> findPokemon() 
	{
		return pkmDao.findAllPokemon();
	}

	public Party getParty() 
	{
		return partyDao.getParty();
	}

	public Party replaceParty(String ... pokemon)
	{
		try
		{
			return partyDao.replaceParty(pokemon);
		} catch (PartyOverflowException e)
		{
			e.printStackTrace();
			System.exit(2);
		} catch (PokemonNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}

	public Party addMember(String name)
	{
		try
		{
			return partyDao.addMember(name);
		} catch (PartyOverflowException e)
		{
			e.printStackTrace();
			System.exit(2);
		} catch (PokemonNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}

	public Party removeMember(String name)
	{
		return partyDao.removeMember(name);
	}
}
