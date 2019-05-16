package pokemon.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import pokemon.exceptions.PartyOverflowException;
import pokemon.model.Party;
import pokemon.model.Pokemon;

/**
 * Represents a pokemon party as a POJO
 * 
 * @author Luke Grammer
 */
public class PartyImpl implements Party, Serializable
{
	private static final long serialVersionUID = -1294062834999445010L;
	
	private Collection<Pokemon> party;

	public PartyImpl()
	{
		party = new ArrayList<Pokemon>();
	}
	
	/**
	 * Constructor for Party given an ArrayList of pokemon
	 * 
	 * @param party is the group of pokemon forming the party
	 */
	public PartyImpl(Collection<Pokemon> party) throws PartyOverflowException
	{
		if (party.size() > 6)
			throw new PartyOverflowException();
		
		this.party = party;
	}

	/**
	 * Returns the party of pokemon as an ArrayList
	 * 
	 * @return the party of pokemon
	 */
	public Collection<Pokemon> getPartyMembers()
	{
		return party;
	}
	
	public int size()
	{
		return party.size();
	}
}
