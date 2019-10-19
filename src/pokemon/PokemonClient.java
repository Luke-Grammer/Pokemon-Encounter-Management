package pokemon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import pokemon.dao.DataAccessManager;
import pokemon.exceptions.*;
import pokemon.model.*;
import pokemon.music.MusicPlayer;

public class PokemonClient {
	public static String VERSION = "1.1";

	private DataAccessManager damgr = null;
	private MusicPlayer mp = null;
	private Scanner sc = null;
	boolean playing = true;
	int volume = MusicPlayer.STARTING_VOLUME;

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
			
			clear(false);
			
			System.out.println("Pokemon Encounter Utility Version " + VERSION);
			System.out.println();
			
			printCurrentParty();

			System.out.println("Main Menu");
			System.out.println("1. Select an opponent");
			System.out.println("2. Replace Party");
			System.out.println("3. Add party members");
			System.out.println("4. Swap party member");
			System.out.println("5. Get pokemon information");
			System.out.println("6. Settings");
			System.out.println("7. Quit");
			System.out.println();
			System.out.print("Please select an option: ");
			String input = sc.nextLine();

			if (input.equals("1"))
			{
				clear(false);
				battleInfo();
			} else if (input.equals("2"))
			{
				clear(false);
				replaceParty();
			} else if (input.equals("3"))
			{
				clear(false);
				addPartyMembers();
			} else if (input.equals("4"))
			{
				clear(false);
				//swapPartyMember();
			} else if (input.equals("5"))
			{
				clear(false);
				pokemonInfo();
			} else if (input.equals("6"))
			{
				clear(false);
				settingsMenu();
			} else if (input.equals("7"))
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

	private void settingsMenu()
	{
		System.out.println("Settings Menu");
		System.out.println("1. Turn music " + (playing ? "off" : "on"));
		System.out.println("2. Change volume (current volume = " + volume + ")");
		System.out.println("3. Return to main menu");
		System.out.println();
		System.out.print("Please select an option: ");
		String input = sc.nextLine();

		if (input.equals("1"))
		{
			if (playing) 
				mp.pause();
			else
				mp.unpause();
			
			playing = !playing;
			System.out.println("\nMusic has been turned " + (playing ? "on" : "off") + "!");
			
		} else if (input.equals("2"))
		{
			System.out.print("\nPlease enter a new volume as an integer [0 - 10]: ");
			if (sc.hasNextInt())
			{
				int temp = sc.nextInt();
				sc.nextLine();
				if (temp < 0 || temp > 10)
				{
					System.out.println("Invalid volume, must be in the range [0 - 10]!");
					System.out.println("Volume not changed, please try again.");
					clear(true);
					settingsMenu();
					return;
				}
				else
				{
					volume = temp;
					mp.setVolume(volume);
					System.out.println("\nVolume has been changed to " + volume + "!");
				}
			}
			else
			{
				sc.nextLine();
				System.out.println("Input could not be interpreted, please try again.");
				clear(true);
				settingsMenu();
				return;
			}
		} else if (input.equals("3"))
		{
			return;
		} else
			System.out.println("Input not recognized, please try again.");
		clear(true);
		settingsMenu();
		return;
	}

	private void pokemonInfo()
	{
		printCurrentParty();
		
		System.out.print("Please enter the name of the pokemon you want to learn more about: ");

		Pokemon p = damgr.findPokemon(sc.nextLine());
			
		if (p != null)
		{
			System.out.println(p.getName());
			
			/*try 
			{
				PrintStream out = new PrintStream(System.out, true, "UTF-8");
				out.println("\t(Japanese name: " + p.getNameJap() + ")");
			} catch(UnsupportedEncodingException e)
			{
				System.out.println("\tCannot print japanese name (unsupported encoding)");
			}*/
			
			System.out.println("Pokedex Number: " + p.getPokedexNumber());
			System.out.println("Description:    " + p.getDesc());
			System.out.println("Primary type:   " + p.getPrimaryType());
			System.out.println("Secondary type: " + ((p.getSecondaryType() == null) ? "None" : p.getSecondaryType()));
		}
		
		clear(true);
	}

	private void printCurrentParty()
	{
		System.out.println(damgr.getParty());
	}

	private void replaceParty()
	{
		printCurrentParty();

		System.out.println("Please enter your new party as a list and press 'enter' when finished.");
		try
		{
			String[] party = sc.nextLine().trim().split("[ ,]+");
			if(party.length == 1)
				if(party[0].isEmpty())
					return;
			
			damgr.replaceParty(party);
			System.out.println("\nParty successfully replaced!");
		} catch (PokemonNotFoundException e)
		{
			System.out.println("\nParty was not successfully replaced. Please add any remaining party members.");
			clear(true);
			addPartyMembers();
			return;
		} catch (PartyOverflowException e)
		{
			System.out.println("\nMaximum party size is 6. Please try again.");
			clear(true);
			replaceParty();
			return;
		}
		clear(true);
	}
	
	public void addPartyMembers()
	{
		printCurrentParty();
		boolean errorFlag = false;
		
		System.out.println("Please enter the pokemon you would like to add as a list and press 'enter' when finished.");
		try
		{
			String[] names = sc.nextLine().trim().split("[ ,]+");

			for (String name : names)
			{			
				if (name.isEmpty())
					return;
				
				try
				{
					damgr.addMember(name);
				} catch (PokemonNotFoundException e)
				{
					errorFlag = true;
				}
			}
		} catch (PartyOverflowException e)
		{
			System.out.println("\nMaximum party size is 6. Some party members could not be added.");
			clear(true);
			addPartyMembers();
			return;
		}
		
		if (errorFlag)
		{
			System.out.println("\nSome party members could not be added. Please try again.");
			clear(true);
			addPartyMembers();
			return;			
		}
		else
			System.out.println("\nParty successfully updated!");			
		
		clear(true);
	}
	
	public void swapPartyMembers()
	{
		printCurrentParty();
		
	}

	public void battleInfo()
	{
		printCurrentParty();

		System.out.print("Please enter an opponent: ");
		String input = sc.nextLine();
		
		if (input.trim().isEmpty())
			return;
		
		Pokemon opponent = damgr.findPokemon(input);
			
		if (opponent != null)
		{
			System.out.println("Opponent: " + opponent);
			System.out.println();
			printAllResults(opponent);
		}
		else
		{
			System.out.println("Opponent could not be found, please ensure the name is typed correctly and try again.");
		}
		clear(true);
	}

	public void printAllResults(Pokemon opponent)
	{
		Party party = damgr.getParty();
		ArrayList<Collection<Pair<Pokemon, Double>>> results = new ArrayList<Collection<Pair<Pokemon, Double>>>();
		
		if (party.size() == 0)
		{
			System.out.println("Your party is empty!");
			return;
		}

		results.add(PokemonTypeInfo.rankAttackModifiers(party, opponent, true));
		results.add(PokemonTypeInfo.rankAttackModifiers(party, opponent, false));
		results.add(PokemonTypeInfo.rankDefenseModifiers(opponent, party, true));
		results.add(PokemonTypeInfo.rankDefenseModifiers(opponent, party, false));
		
		String[] info = { "primary attack damage to ", "secondary attack damage to ", "damage taken from the primary type of ", "damage taken from the secondary type of"};
		
		int i = 0;
		for(Collection<Pair<Pokemon, Double>> result : results)
		{
			if (result.size() > 0)
			{
				System.out.println("Current party members ranked by " + info[i++] + opponent.getName() + ": ");
				printResults(result);
			}
			System.out.println();
		}
	}

	public void printResults(Collection<Pair<Pokemon, Double>> results)
	{
		int i = 0;
		for (Pair<Pokemon, Double> result : results)
		{
			System.out.println("\t" + ++i + ") " + result.getFirst().getName() + " (x" + result.getSecond() + ")");
		}
	}

	private void clear(boolean pause)
	{
		if (pause)
		{
			System.out.println("\nPress 'enter' to continue...");
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
