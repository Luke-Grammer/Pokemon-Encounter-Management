package pokemon.dao;

import pokemon.exceptions.NotInPartyException;
import pokemon.exceptions.PartyOverflowException;
import pokemon.exceptions.PokemonNotFoundException;
import pokemon.model.Party;

public interface PartyDao
{
	Party getParty();
	
	void clearParty();
	
	void replaceParty(String ... names) throws PartyOverflowException, PokemonNotFoundException;
	
	void addMember(String name) throws PartyOverflowException, PokemonNotFoundException;
	
	void removeMember(String name) throws NotInPartyException;
}
