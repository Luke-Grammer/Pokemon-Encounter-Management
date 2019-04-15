package pokemonDB.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a pokemon party as a POJO
 * 
 * @author luke
 */
public class Party implements Serializable
{
	private static final long serialVersionUID = -1294062834999445010L;
	
	private ArrayList<Pokemon> party;

	/**
	 * Constructor for Party given an ArrayList of pokemon
	 * 
	 * @param party is the group of pokemon forming the party
	 */
	public Party(ArrayList<Pokemon> party)
	{
		assert party.size() <= 6 : "Party overflow";
		assert party.size() >= 0 : "Party underflow"; 
		
		this.party = party;
	}

	/**
	 * Returns the party of pokemon as an ArrayList
	 * 
	 * @return the party of pokemon
	 */
	public ArrayList<Pokemon> getPartyMembers()
	{
		return party;
	}

}
