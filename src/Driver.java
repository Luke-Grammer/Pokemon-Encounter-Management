import java.util.Collection;

import pokemon.Pair;
import pokemon.DataAccessManager;
import pokemon.PokemonTypeInfo;
import pokemon.model.Party;
import pokemon.model.Pokemon;

public class Driver
{
	public static final String OPPONENT = "squirtle";

	public static void main(String[] args)
	{
		DataAccessManager damgr = new DataAccessManager();

		Party party = null;
		Pokemon opponent = null;
		party = damgr.replaceParty("Pikachu", "Bulbasaur", "Squirtle", "Charmander", "MissingNo.", "Spearow");
		opponent = damgr.findPokemon(OPPONENT);

		int i = 0;
		System.out.println("Current party members ranked by primary attack damage to " + opponent.getName() + ": ");

		Collection<Pair<Pokemon, Double>> results = PokemonTypeInfo.rankAttackModifiers(party.getPartyMembers(),
				opponent, true);
		if (results.size() != 0)
			for (Pair<Pokemon, Double> result : results)
			{
				i++;
				System.out.println("\t" + i + ") " + result.getFirst().getNameJap() + " (x" + result.getSecond() + ")");
			}
		else
			System.out.println("Empty!");
	}
}