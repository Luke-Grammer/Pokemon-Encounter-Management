package pokemon.dao;

import pokemon.exceptions.PartyOverflowException;
import pokemon.exceptions.PokemonNotFoundException;
import pokemon.model.Party;

public interface PartyDao
{
	Party getParty();
	
	void clearParty();
	
	Party replaceParty(String ... names) throws PartyOverflowException, PokemonNotFoundException;
	
	Party addMember(String name) throws PartyOverflowException, PokemonNotFoundException;
	
	Party removeMember(String name) throws PokemonNotFoundException;
}
