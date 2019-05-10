import pokemon.PokemonClient;
import pokemon.DataAccessManager;
import pokemon.PokemonTypeInfo;
import pokemon.model.Party;
import pokemon.model.Pokemon;

public class Driver
{
	public static final String OPPONENT = "squirtle";

	public static void main(String[] args)
	{
		PokemonClient client = new PokemonClient();
		DataAccessManager damgr = new DataAccessManager();
		Party party = damgr.replaceParty("Pikachu", "Bulbasaur", "Squirtle", "Charmander", "Weedle", "Spearow");
		Pokemon opponent = damgr.findPokemon(OPPONENT);

		
		
		 client.printResults(opponent, PokemonTypeInfo.rankAttackModifiers(party.getPartyMembers(),
				opponent, true));
	}
}