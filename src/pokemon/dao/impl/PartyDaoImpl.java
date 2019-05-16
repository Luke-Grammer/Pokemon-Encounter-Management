package pokemon.dao.impl;

import java.util.Collection;

import pokemon.dao.PartyDao;
import pokemon.dao.PokemonDao;
import pokemon.exceptions.NotInPartyException;
import pokemon.exceptions.PartyOverflowException;
import pokemon.exceptions.PokemonNotFoundException;
import pokemon.file.PokemonFile;
import pokemon.model.Party;
import pokemon.model.Pokemon;
import pokemon.model.impl.PartyImpl;

public class PartyDaoImpl implements PartyDao
{	
	private PokemonDao pkmDao  = null;
	private PokemonFile pkmFile = null;
	
    public PartyDaoImpl(PokemonDao pkmDao2, PokemonFile pkmFile) 
    {
    	this.pkmFile = pkmFile;
    	this.pkmDao = pkmDao2;
    }

	@Override
	public Party getParty()
	{
		try {
			return new PartyImpl(pkmFile.readAllPokemon());
		} catch (PartyOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new PartyImpl();
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
		if (names.length > 6)
		{
			System.out.println("Attempting to create party with too many pokemon! (" + names.length + ")");
			throw new PartyOverflowException();
		}
				
		clearParty();
		boolean errorFlag = false;
		
		for(String name : names)
		{
			if (name.length() > 1)
			{
				Pokemon p = pkmDao.findPokemon(name);
				if (p != null)
					pkmFile.writePokemon(p);
				else
					errorFlag = true;
			}
		}
		
		if(errorFlag)
			throw new PokemonNotFoundException();
		
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
		
		Pokemon p = pkmDao.findPokemon(name);
		if (p != null)
			pkmFile.writePokemon(p);
		else
			throw new PokemonNotFoundException();
		return getParty();
	}

	@Override
	public Party removeMember(String name) throws NotInPartyException
	{
		Collection<Pokemon> party = getParty().getPartyMembers();
		boolean errorFlag = true;
		
		clearParty();
		for (Pokemon p : party)
		{
			if (p.getName().toLowerCase() != name.toLowerCase())
				pkmFile.writePokemon(p);
			else
				errorFlag = false;
		}
		
		if (errorFlag)
			throw new NotInPartyException();
		
		return getParty();
	}
}