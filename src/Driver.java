import pokemon.PokemonClient;

public class Driver
{
	public static void main(String[] args)
	{
		PokemonClient client = new PokemonClient();

		client.mainMenu();
		
		client.close();
		System.exit(0);
	}
}