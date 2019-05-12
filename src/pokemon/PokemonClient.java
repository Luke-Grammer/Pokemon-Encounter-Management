package pokemon;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Scanner;

import pokemon.dao.DataAccessManager;
import pokemon.exceptions.PartyOverflowException;
import pokemon.exceptions.PokemonNotFoundException;
import pokemon.model.Party;
import pokemon.model.Pokemon;
import pokemon.music.MusicPlayer;

public class PokemonClient {
	public static String VERSION = "1.0";

	private DataAccessManager damgr = null;
	MusicPlayer mp = null;
	Scanner sc = null;

	public PokemonClient()
	{
		mp = new MusicPlayer();
		damgr = new DataAccessManager();
		sc = new Scanner(System.in);
		mp.start();
	}

	public void close()
	{
		try
		{
			mp.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		sc.close();
	}

	public void mainMenu()
	{
		while (true)
		{
			printCurrentParty();

			System.out.println("\nMain Menu");
			System.out.println("1. Select an opponent");
			System.out.println("2. Change party");
			System.out.println("3. Get pokemon information");
			System.out.println("4. Settings");
			System.out.println("5. Quit");

			System.out.print("\nPlease select an option: ");
			String input = sc.nextLine();

			if (input.startsWith("1"))
			{
				clear(false);
				battleInfo();
			} else if (input.startsWith("2"))
			{
				clear(false);
				changeParty();
			} else if (input.startsWith("3"))
			{
				clear(false);
				pokemonInfo();
			} else if (input.startsWith("4"))
			{
				clear(false);
				settings();
			} else if (input.startsWith("5"))
			{
				break;
			} else
			{
				System.out.println("Input not recognized, please try again.");
				clear(true);
				continue;
			}
		}
	}

	private void settings()
	{
		System.out.println("Settings coming soon!");
		clear(true);
	}

	private void pokemonInfo()
	{
		printCurrentParty();
		
		System.out.print("\nPlease enter the name of the pokemon you want to learn more about: ");
		try
		{
			Pokemon p = damgr.findPokemon(sc.nextLine());
			System.out.print(p.getName());
			try 
			{
				PrintStream out = new PrintStream(System.out, true, "UTF-8");
				out.println("\t(Japanese name: " + p.getNameJap() + ")");
			} catch(UnsupportedEncodingException e)
			{
				System.out.println("\tCannot print japanese name (unsupported encoding)");
			}
			System.out.println("Pokedex Number: " + p.getPokedexNumber());
			System.out.println("Description:    " + p.getDesc());
			System.out.println("Primary type:   " + p.getPrimaryType());
			System.out.println("Secondary type: " + ((p.getSecondaryType() == null) ? "None" : p.getSecondaryType()));
		} catch (PokemonNotFoundException e) {}
		System.out.println();
		clear(true);
	}

	private void printCurrentParty()
	{
		clear(false);
		
		System.out.println("Pokemon Encounter Utility Version " + VERSION + "\n");
		Party party = damgr.getParty();
		int i = 0;
		if (party.getPartyMembers().size() == 0)
		{
			System.out.println("Your current party is empty!");
		} else
		{
			System.out.println("Current party:");
			for (Pokemon p : party.getPartyMembers())
			{
				System.out.println("\t" + ++i + ") " + p.getName() + "\t(" + p.getPrimaryType().toUpperCase()
						+ (p.getSecondaryType() == null ? "" : ("/" + p.getSecondaryType().toUpperCase())) + ")");
			}
		}
		for (; i < 6; i++)
		{
			System.out.println();
		}
	}

	private void changeParty()
	{
		printCurrentParty();

		System.out.println("\nPlease enter your new party as a list and press 'enter' when finished.");
		try
		{
			damgr.replaceParty(sc.nextLine().trim().toLowerCase().split("[ ,]+"));
			System.out.println("\nParty successfully changed!\n");
		} catch (PokemonNotFoundException e)
		{
			System.out.println("\nParty was not successfully changed. Please try again.");
			clear(true);
			changeParty();
			return;
		} catch (PartyOverflowException e)
		{
			System.out.println("\nMaximum party size is 6. Please try again.");
			clear(true);
			changeParty();
			return;
		}
		clear(true);
	}

	public void battleInfo()
	{
		printCurrentParty();

		System.out.print("\nPlease enter an opponent: ");
		String input = sc.nextLine();

		Pokemon opponent = null;
		try
		{
			opponent = damgr.findPokemon(input);

			System.out.println("Opponent: " + opponent.getName() + "\t(" + opponent.getPrimaryType().toUpperCase()
					+ (opponent.getSecondaryType() == null ? "" : ("/" + opponent.getSecondaryType().toUpperCase()))
					+ ")\n");
			printAllResults(opponent);
		} catch (PokemonNotFoundException e)
		{
		}
		clear(true);
	}

	public void printAllResults(Pokemon opponent)
	{
		Party party = damgr.getParty();
		Collection<Pair<Pokemon, Double>> results;
		
		if (party.getPartyMembers().size() == 0)
		{
			System.out.println("Your party is empty!");
			return;
		}

		results = PokemonTypeInfo.rankAttackModifiers(party, opponent, true);
		if (results.size() > 0)
		{
			System.out.println("Current party members ranked by primary attack damage to " + opponent.getName() + ": ");
			printResults(results);
		}

		results = PokemonTypeInfo.rankAttackModifiers(party, opponent, false);
		if (results.size() > 0)
		{
			System.out
					.println("Current party members ranked by secondary attack damage to " + opponent.getName() + ": ");
			printResults(results);
		}

		results = PokemonTypeInfo.rankDefenseModifiers(opponent, party, true);
		if (results.size() > 0)
		{
			System.out.println(
					"Current party members ranked by damage taken from " + opponent.getName() + "'s primary type: ");
			printResults(results);
		}

		results = PokemonTypeInfo.rankDefenseModifiers(opponent, party, false);
		if (results.size() > 0)
		{
			System.out.println(
					"Current party members ranked by damage taken from " + opponent.getName() + "'s secondary type: ");
			printResults(results);
		}
	}

	public void printResults(Collection<Pair<Pokemon, Double>> results)
	{
		int i = 0;
		for (Pair<Pokemon, Double> result : results)
		{
			System.out.println("\t" + ++i + ") " + result.getFirst().getName() + " (x" + result.getSecond() + ")");
		}
		System.out.println();
	}

	private void clear(boolean pause)
	{
		if (pause)
		{
			System.out.println("Press 'enter' to continue...");
			sc.nextLine();
		}

		try
		{
			if (System.getProperty("os.name").startsWith("Windows"))
			{
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else
			{
				Runtime.getRuntime().exec("clear");
			}
		} catch (IOException | InterruptedException e)
		{
			System.out.println("Could not clear console!");
			e.printStackTrace();
		}
	}
}
