package pokemon.dao.impl;

import java.util.Collection;

import pokemon.dao.PartyDao;
import pokemon.exceptions.PartyOverflowException;
import pokemon.exceptions.PokemonNotFoundException;
import pokemon.file.PokemonFile;
import pokemon.model.Party;
import pokemon.model.Pokemon;

public class PartyDaoImpl implements PartyDao
{	
	private PokemonDaoImpl pkmDao  = null;
	private PokemonFile pkmFile = null;
	
    public PartyDaoImpl(PokemonDaoImpl pkmDao, PokemonFile pkmFile) 
    {
    	this.pkmFile = pkmFile;
    	this.pkmDao = pkmDao;
    }

	@Override
	public Party getParty()
	{
		try {
			return new Party(pkmFile.readAllPokemon());
		} catch (PartyOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Party();
		}
	}

	@Override
	public void clearParty()
	{
		pkmFile.clear();
	}
	
	@Override
	public Party replaceParty(String ... names) throws PartyOverflowException, PokemonNotFoundException
	{
		clearParty();
		
		if (names.length > 6)
		{
			System.out.println("Attempting to create party with too many pokemon! (" + names.length + ")");
			throw new PartyOverflowException();
		}
		
		for(String name : names)
		{
			if (name.length() > 1)
			{
				Pokemon p = pkmDao.findPokemon(name);
				pkmFile.writePokemon(p);
			}
		}
		return getParty();
	}

	@Override
	public Party addMember(String name) throws PartyOverflowException, PokemonNotFoundException
	{
		if (getParty().getPartyMembers().size() > 5)
		{
			System.out.println("Attempting to add a pokemon to full party!");
			throw new PartyOverflowException();
		}
		
		pkmFile.writePokemon(pkmDao.findPokemon(name));
		return getParty();
	}

	@Override
	public Party removeMember(String name) throws PokemonNotFoundException
	{
		Collection<Pokemon> party = getParty().getPartyMembers();
		boolean found = false;
		clearParty();
		for (Pokemon p : party)
		{
			if (p.getName().toLowerCase() != name.toLowerCase())
				pkmFile.writePokemon(p);
			else
				found = true;
		}
		
		if (!found)
			throw new PokemonNotFoundException();
		
		return getParty();
	}
}