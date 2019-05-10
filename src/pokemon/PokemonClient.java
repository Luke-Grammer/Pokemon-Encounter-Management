package pokemon;

import java.util.Collection;

import pokemon.model.Pokemon;

public class PokemonClient
{
	private DataAccessManager damgr = null;
	
	public PokemonClient()
	{
		damgr = new DataAccessManager();
	}
	
	public void printResults(Pokemon opponent, Collection<Pair<Pokemon, Double>> results)
	{
		System.out.println("Current party members ranked by primary attack damage to " + opponent.getName() + ": ");
		int i = 0;
		if (results.size() != 0)
			for (Pair<Pokemon, Double> result : results)
			{
				i++;
				System.out.println("\t" + i + ") " + result.getFirst().getName() + " (x" + result.getSecond() + ")");
			}
		else
			System.out.println("Empty!");
	}
}
