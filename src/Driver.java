
import java.util.Scanner;

import ORMexample.PokemonDBClient;;

public class Driver
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		PokemonDBClient pc = null;

		try
		{
			pc = new PokemonDBClient();
			
			System.out.println("Connected to Pokedex\n");

			// *************************************************************************************
			// TEAM UPDATE
			// *************************************************************************************

			String input = "";
			if (!pc.displayParty())
			{
				input = "y";
			} else
			{
				System.out.print("\nChange party (y/n): ");
				input = sc.nextLine().trim().toLowerCase();
			}

			if (input.startsWith("y"))
			{
				do {
					System.out.print("\nSwap individual pokemon, add pokemon, or replace the team? (s/a/r): ");
					input = sc.nextLine().trim().toLowerCase();
					if(input.startsWith("s"))
					{
						while (pc.swapPartyPokemon() < 0) 
							continue;
						break;
					}
					else if(input.startsWith("a"))
					{
						while (pc.changeParty(false) < 0) 
							continue;
						break;
					}
					else if(input.startsWith("r"))
					{
						while (pc.changeParty(true) < 0) 
							continue;
						break;
					}
					else 
						System.out.println("Unrecognized input, please try again.");
				} while (true);
			
				System.out.println("");
				mgr.displayParty();
			}

			System.out.println("");

			// *************************************************************************************
			// MAIN LOOP
			// *************************************************************************************

			while(true) // Loop program until user enters 'quit'
			{
				System.out.println("Please select an action or enter 'quit' to quit:");
				System.out.println("1. Evaluate opponent");
				System.out.println("2. Update party (WIP)");
				System.out.println("3. Pokemon information (WIP)");
				

				input = sc.nextLine().trim().toLowerCase(); // Collect user input for opponent
				if (input.startsWith("q"))
					break;
				
				else if (input.startsWith("1"))
				{
					while (pc.evaluateOpponent() < 0);
				}
				else if (input.startsWith("2"))
				{
					
				}
				else if (input.startsWith("3"))
				{
					
				}

			}
			
			sc.close();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		} finally
		{
			if (pc != null)
				pc.close();
		}
	}
}