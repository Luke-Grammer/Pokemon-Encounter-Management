import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PokemonDBClient
{
	public static final String protocol = "jdbc:mariadb://localhost/";
	public static final String dbName = "PokemonDB"; // Database name

	private Scanner sc;
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;

	public PokemonDBClient() throws RuntimeException
	{
		sc = new Scanner(System.in);
		try
		{
			conn = DriverManager.getConnection(protocol + dbName); // Connect to MariaDB using DriverManager
			conn.setAutoCommit(false); // Turn off autocommit
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new RuntimeException("An unexpected error occured. Attempting to close connection to Pokedex.");
		}
	}

	public void close()
	{
		if (sc != null)
			sc.close();
		
		try
		{
			if (rs != null) rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		try
		{
			if (stmt != null) stmt.close();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		try
		{
			if (conn != null) conn.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		System.out.println("\nConnection closed.");
	}

	private ArrayList<String> getParty()
	{
		ArrayList<String> party = new ArrayList<String>();
		try
		{
			stmt = conn.prepareStatement("select name from party");
			rs = stmt.executeQuery();
			
			while (rs.next())
			{
				party.add(rs.getString(1));
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new RuntimeException("An unexpected error occured while updating the party");
		}
		return party;
	}

	public boolean displayParty()
	{
		ArrayList<String> party = getParty();
		if (party.size() == 0)
		{
			System.out.println("It looks like your current party is empty!");
			return false;
		}

		System.out.println("Current party:");
		for (String pokemon : party)
		{
			System.out.println(pokemon);
		}

		return true;
	}

	public int changeParty(boolean delete)
	{
		String[] line;

		try
		{
			if (delete)
			{
				stmt = conn.prepareStatement("delete from party");
				stmt.executeUpdate();
			}

			System.out.print("\nPlease list the pokemon in your party as a comma separated list: ");
			line = sc.nextLine().split(","); // Collect team pokemon as comma separated list

			if (line.length + getParty().size() > 6)
			{
				System.out.println("ERROR: Too many pokemon specified, max party size is 6. Please try again.");
				return -1;
			}

			for (int i = 0; i < line.length; i++)
			{ // Add each of them to the team
				line[i] = line[i].trim();

				if (line[i].isEmpty())
				{
					System.out.println(
							"ERROR: Every name must contain at least one non whitespace character. Please try again.");
					return -1;
				}

				try
				{
					stmt = conn.prepareStatement("insert into party (name) values (?)");
					stmt.setString(1, line[i]);
					stmt.executeUpdate();
				} catch (SQLException e)
				{
					conn.rollback();
					System.out.println("ERROR: Failed to insert " + line[i] + "!");
					System.out.println("Is it a registered pokemon?");
					return -1;
				}
			}

			conn.commit();
			return 0;
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(
					"An unexpected error occured when changing the party, attempting to close connection to Pokedex");
		}
	}

	public int swapPartyPokemon()
	{
		String[] line1, line2;

		try
		{
			System.out.print("\nPlease enter the pokemon in your party you want to tag out: ");
			line1 = sc.nextLine().split(","); // Collect team pokemon as comma separated list

			int size = getParty().size();
			if (line1.length > size)
			{
				System.out.println("ERROR: Too many pokemon specified, there are only " + size + " pokemon in your party. Please try again.");
				return -1;
			}

			for (int i = 0; i < line1.length; i++)
			{ // Add each of them to the team
				line1[i] = line1[i].trim();

				if (line1[i].isEmpty())
				{
					System.out.println(
							"ERROR: Every name must contain at least one non whitespace character. Please try again.");
					return -1;
				}
				try
				{
					stmt = conn.prepareStatement("delete from party where name = ?");
					stmt.setString(1, line1[i]);
					stmt.executeUpdate();
				} catch (SQLException e)
				{
					conn.rollback();
					System.out.println("ERROR: Failed to remove " + line1[i] + "!");
					System.out.println("Is it in your party?");
					return -1;
				}					
			}
			
			System.out.print("\nPlease enter the pokemon you want to replace them with: ");
			line2 = sc.nextLine().split(","); // Collect team pokemon as comma separated list

			if (line2.length != line1.length)
			{
				try
				{
					conn.rollback();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
				System.out.println("ERROR: Number of pokemon do not match. Please try again.");
				return -1;
			}

			for (int i = 0; i < line2.length; i++)
			{ // Add each of them to the team
				line2[i] = line2[i].trim();

				if (line2[i].isEmpty())
				{
					System.out.println(
							"ERROR: Every name must contain at least one non whitespace character. Please try again.");
					return -1;
				}				
				try
				{
					stmt = conn.prepareStatement("insert into party (name) values (?)");
					stmt.setString(1, line2[i]);
					stmt.executeUpdate();
				} catch (SQLException e)
				{
					conn.rollback();
					System.out.println("ERROR: Failed to insert " + line2[i] + "!");
					System.out.println("Is it a registered pokemon?");
					return -1;
				}
			}
			
			conn.commit();
			return 0;
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(
					"An unexpected error occured when changing the party, attempting to close connection to Pokedex");
		}
	}
	
	public int evaluateOpponent()
	{
		System.out.print("\nPlease enter an opponent: ");

		String input = sc.nextLine().trim(); // Collect user input for opponent

		// DEFENSE **************************************************************

		System.out.println("\nParty ranked by resistance to " + input + ":");	
		ArrayList<ResultPair> pDefenseResult = rankDefense(input);
		
		if (pDefenseResult == null)
			return -1;
		
		for (int i = 0; i < pDefenseResult.size(); i++) // Loop through all party pokemon with relevant type
		{
			System.out.print((i + 1) + ") ");
			System.out.printf("%-15s", pDefenseResult.get(i).name);
			System.out.println(" (x" + pDefenseResult.get(i).modifier + " dam.)");
		}

		// ATTACK **************************************************************

		System.out.println("\nParty ranked by primary type damage to " + input + ":");	
		ArrayList<ResultPair> pAttackResult = rankAttack(input, 1);
		
		if (pAttackResult == null)
			return -1;
		
		for (int i = 0; i < pAttackResult.size(); i++) // Loop through all party pokemon with relevant type
		{
			System.out.print((i + 1) + ") ");
			System.out.printf("%-15s", pAttackResult.get(i).name);
			System.out.println(" (x" + pAttackResult.get(i).modifier + " dam.)");
		}
		
		System.out.println("\nParty ranked by secondary type damage to " + input + ":");
		ArrayList<ResultPair> sAttackResult = rankAttack(input, 2);
		
		if (sAttackResult == null)
			return -1;
		
		for (int i = 0; i < sAttackResult.size(); i++) // Loop through all party pokemon with relevant type
		{
			System.out.print((i + 1) + ") ");
			System.out.printf("%-15s", sAttackResult.get(i).name);
			System.out.println(" (x" + sAttackResult.get(i).modifier + " dam.)");
		}
		
		System.out.println("");
		
		ArrayList<ResultPair> rankings = new ArrayList<ResultPair>();
		for (int i = 0; i < pAttackResult.size(); i++)
		{
			ResultPair candidate = new ResultPair(pAttackResult.get(i));
			Double rank = 0.0;
			
			System.out.println("Candidate: " + candidate.name);
			System.out.println("Candidate primary attack modifier: " + candidate.modifier);
			for (int j = 0; j < pAttackResult.size(); j++)
			{
				System.out.println("Comparing " + candidate.name + " to " + pAttackResult.get(j).name + " (modifier " + pAttackResult.get(j).modifier + ")");
				if(Math.abs(candidate.modifier - pAttackResult.get(j).modifier) > 0.01) 
				{
					System.out.println("Not equal, increasing rank");
					rank += 100.0;
				}
				else
					break;
			}
			
			for (int j = 0; j < pDefenseResult.size(); j++)
			{
				if (candidate.name.equals(pDefenseResult.get(j).name))
				{
					candidate.modifier = pDefenseResult.get(j).modifier;
					break;
				}
			}
				
			for (int j = 0; j < pDefenseResult.size(); j++)
			{
				if(Math.abs(candidate.modifier - pDefenseResult.get(j).modifier) > 0.01) 
				{
					rank += 10.0;
				}
				else
					break;
			}
			
			for (int j = 0; j < sAttackResult.size(); j++)
			{
				if (candidate.name.equals(sAttackResult.get(j).name))
				{
					candidate = sAttackResult.get(j);
					break;
				}				
			}
			
			for (int j = 0; j < sAttackResult.size(); j++)
			{
				if(Math.abs(candidate.modifier - sAttackResult.get(j).modifier) > 0.01) 
					rank += 1.0;
				else
					break;
			}
			
			rankings.add(new ResultPair(candidate.name, rank));
		}
		
		System.out.println("Rankings");
		for (ResultPair rank : rankings)
			System.out.println(rank.name + " " + rank.modifier);
		System.out.println("The best match for " + input + " in your party is " + Collections.min(rankings).name + "\n");
		
		return 0;
	}
	
	public ArrayList<ResultPair> rankDefense(String input)
	{
		ArrayList<ResultPair> results = new ArrayList<ResultPair>();
		
		try
		{
			stmt = conn.prepareStatement("select type1 from pokemon where name = ?");

			stmt.setObject(1, input);
			rs = stmt.executeQuery();

			if (!rs.next())
			{
				System.out.println("Could not find information on " + input);
				System.out.println("");
				return null;
			}
			
			String type = rs.getString(1);
			
			stmt = conn.prepareStatement("select name from party");
			rs = stmt.executeQuery();
			
			rs.beforeFirst();
			while(rs.next()) // Loop through all result rows
			{
				results.add(new ResultPair(rs.getString(1), rs.getDouble(2)));
			}

			Collections.sort(results);
			
			conn.commit();
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new RuntimeException("An unexpected error occured. Attempting to close connection to Pokedex.");
		}
		return results;
	}

	public ArrayList<ResultPair> rankAttack(String input, int type)
	{
		PreparedStatement stmt2 = null;
		ArrayList<String> party = getParty();
		ArrayList<ResultPair> results = new ArrayList<ResultPair>();

		if (type != 1 && type != 2)
		{
			System.out.println("Invalid type selection!");
			return null;
		}

		try
		{
			for (int i = 0; i < party.size(); i++)
			{
				stmt = conn.prepareStatement("select type" + type + " from pokemon where name = ?");
				stmt.setObject(1, party.get(i));
				rs = stmt.executeQuery();

				if (!rs.next())
				{
					System.out.println("Could not find type information for " + party.get(i));
					System.out.println("");
					return null;
				}

				stmt2 = conn.prepareStatement("select " + rs.getString(1) + " from pokemon where name = ?");
				stmt2.setString(1, input);
				rs = stmt2.executeQuery();

				if (!rs.next())
				{
					System.out.println("Could not find information on " + input);
					System.out.println("");
					return null;
				}

				if (rs.getDouble(1) > 0)
					results.add(new ResultPair(party.get(i), rs.getDouble(1)));
			}

			Collections.sort(results, Collections.reverseOrder());
			
			stmt2.close();
			conn.commit();
		} catch (SQLException e)
		{
			try
			{
				if (stmt2 != null)
					stmt2.close();

			} catch (SQLException e1)
			{
				e.printStackTrace();
			}

			e.printStackTrace();
			throw new RuntimeException("An unexpected error occured. Attempting to close connection to Pokedex.");
		}
		return results;
	}
}