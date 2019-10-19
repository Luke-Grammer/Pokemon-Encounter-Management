import pokemon.PokemonClient;

/**
 * Main driver class for program. 
 * Creates a client and starts the main menu loop.
 * After loop ends the Driver closes the client and terminates the program.
 * 
 * @author Luke Grammer
 */
public class Driver {
	
	/**
	 * Creates a client and starts the main menu loop.
	 * After loop ends the client is closed and the program is terminated.
	 */
	public static void main(String[] args)
	{
		PokemonClient client = new PokemonClient();

		client.mainMenu();

		client.close();
		System.exit(0);
	}
}