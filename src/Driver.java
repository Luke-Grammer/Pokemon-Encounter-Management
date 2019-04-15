import pokemonDB.DBManager;
import pokemonDB.model.Party;
import pokemonDB.model.Pokemon;

public class Driver
{
	public static void main(String[] args) throws Exception {
		long ctm1, ctm2;
        ctm1 = java.lang.System.nanoTime();//get current timer value
		DBManager DBmgr = new DBManager();
		
		Party party = DBmgr.replaceParty("Pikachu", "Bulbasaur", "Squirtle", "Charmander", "Weedle", "Spearow");
		int i = 0;
		for (Pokemon pkm : party.getPartyMembers())
		{
			i++;
			System.out.println(i + ") " + pkm.getName() + " (" + pkm.getPrimaryType() + ")");
		}
		
        ctm2 = java.lang.System.nanoTime();//get current timer value
		System.out.println("Seconds to run View: "+ (double)(ctm2 - ctm1)/1000000000d);//+"\n"
	}
}