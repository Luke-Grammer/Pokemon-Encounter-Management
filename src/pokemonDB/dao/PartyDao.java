package pokemonDB.dao;

import pokemonDB.model.Party;

public interface PartyDao
{
	Party getParty();
	
	void clearParty();
	
	Party replaceParty(String ... pokemon);
	
	Party addMember(String name); // TODO: Implement throw when name not in DB
	
	Party removeMember(String name); //TODO: Implement throw when name not in DB
}
