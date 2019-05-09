import java.util.Collection;

import pokemon.Pair;
import pokemon.PokemonManager;
import pokemon.PokemonTypeInfo;
import pokemon.model.Party;
import pokemon.model.Pokemon;

public class Driver
{
	public static final String OPPONENT = "squirtle";

	public static void main(String[] args)
	{
		PokemonManager pkmgr = new PokemonManager();

		Party party = null;
		Pokemon opponent = null;
		party = pkmgr.replaceParty("Pikachu", "Bulbasaur", "Squirtle", "Charmander", "Weedle", "Spearow");
		opponent = pkmgr.findPokemon(OPPONENT);

		int i = 0;
		System.out.println("Current party members ranked by primary attack damage to " + opponent.getName() + ": ");

		Collection<Pair<Pokemon, Double>> results = PokemonTypeInfo.rankAttackModifiers(party.getPartyMembers(),
				opponent, true);
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