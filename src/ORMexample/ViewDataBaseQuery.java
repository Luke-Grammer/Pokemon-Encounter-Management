
package ORMexample;

import ORMexample.DBManager;
import ORMexample.model.DBPokemon;

public class ViewDataBaseQuery {

	public static void main(String[] args) throws Exception {
		long ctm1, ctm2;
        ctm1 = java.lang.System.nanoTime();//get current timer value
		DBManager DBmgr = new DBManager();
		
         
        for(DBPokemon pkm: DBmgr.findPokemon()) {//get games played in
        	
			System.out.println(pkm.getName() + " has pokedex number " + pkm.getPokedexNumber() + "\n\tPrimary type: " + pkm.getPrimaryType() + "\n\tSecondary type: " + pkm.getSecondaryType() + "\n");
        }
        ctm2 = java.lang.System.nanoTime();//get current timer value
		System.out.println("Seconds to run View: "+ (double)(ctm2 - ctm1)/1000000000d);//+"\n"
	}
}