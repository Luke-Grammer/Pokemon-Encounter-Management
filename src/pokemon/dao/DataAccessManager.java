package pokemon.dao;

import java.util.Collection;

import pokemon.dao.impl.PartyDaoImpl;
import pokemon.dao.impl.PokemonDaoImpl;
import pokemon.exceptions.PartyOverflowException;
import pokemon.exceptions.PokemonNotFoundException;
import pokemon.file.PokemonFile;
import pokemon.model.Party;
import pokemon.model.Pokemon;

public class DataAccessManager {
	private static final String FILENAME = "pokemon.csv";
	private static final String PARTY_FILENAME = "party.csv";

	private PokemonDaoImpl pkmDao = null;
	private PartyDaoImpl partyDao = null;
	private PokemonFile pkmFile = null;
	private PokemonFile partyFile = null;

	public DataAccessManager() 
	{
		pkmFile = new PokemonFile(FILENAME);
		pkmDao = new PokemonDaoImpl(pkmFile);

		partyFile = new PokemonFile(PARTY_FILENAME);
		partyDao = new PartyDaoImpl(pkmDao, partyFile); // TODO: Implement multiple parties?
	}

	public Pokemon findPokemon(String name) throws PokemonNotFoundException
	{
		return pkmDao.findPokemon(name);
	}

	public Collection<Pokemon> findPokemon() 
	{
		return pkmDao.findAllPokemon();
	}

	public Party getParty() 
	{
		return partyDao.getParty();
	}

	public Party replaceParty(String ... pokemon) throws PartyOverflowException, PokemonNotFoundException
	{
		return partyDao.replaceParty(pokemon);
	}

	public Party addMember(String name) throws PartyOverflowException, PokemonNotFoundException
	{
		return partyDao.addMember(name);
	}

	public Party removeMember(String name) throws PokemonNotFoundException
	{
		return partyDao.removeMember(name);
	}
}
