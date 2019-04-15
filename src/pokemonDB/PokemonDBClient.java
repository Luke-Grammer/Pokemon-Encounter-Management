
package pokemonDB;

import java.util.ArrayList;

import pokemonDB.model.Pokemon;

public class PokemonDBClient 
{

	private DBManager mgr = null;
	
	// Constructor initializes dbmanager
	public PokemonDBClient()
	{
		mgr = new DBManager();
	}
	
	public ArrayList<Pokemon> rankAttackModifier(ArrayList<Pokemon> pokemon, boolean primary)
	{
		return pokemon;
		
	}
	
	public void close()
	{
		if (mgr != null)
		{
			mgr.close();
		}
	}
}