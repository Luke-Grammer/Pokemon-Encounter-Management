import java.util.ArrayList;

import pokemonDB.DBManager;
import pokemonDB.Pair;
import pokemonDB.PokemonTypeInfo;
import pokemonDB.model.Party;
import pokemonDB.model.Pokemon;

public class Driver
{
	public static final String OPPONENT = "squirtle";
	
	public static void main(String[] args) throws Exception {
		//long ctm1, ctm2;
        //ctm1 = java.lang.System.nanoTime();//get current timer value
		
		DBManager DBmgr = new DBManager();
		
		Party party = DBmgr.replaceParty("Pikachu", "Bulbasaur", "Squirtle", "Charmander", "Weedle", "Spearow");
		Pokemon opponent = DBmgr.findPokemon(OPPONENT);
		
		int i = 0;
		System.out.println("Current party members ranked by primary attack damage to " + opponent.getName() + ": ");
		
		ArrayList<Pair<Pokemon, Double>> results = PokemonTypeInfo.rankAttackModifiers(party.getPartyMembers(), opponent, true);
		if(results.size() != 0)
			for (Pair<Pokemon, Double> result : results)
			{
				i++;
				System.out.println("\t" + i + ") " + result.getFirst().getName() + " (x" + result.getSecond() + ")");
			}
		else
			System.out.println("Empty!");
		
        //ctm2 = java.lang.System.nanoTime();//get current timer value
		//System.out.println("Seconds to run View: "+ (double)(ctm2 - ctm1)/1000000000d);//+"\n"
	}
}