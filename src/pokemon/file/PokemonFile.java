package pokemon.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import pokemon.model.Pokemon;

public class PokemonFile {
	
	private File file;
	
	public PokemonFile(String filename) 
	{
		file = new File(filename);
	}
	
	public void writePokemon(Pokemon pokemon)
	{
		try {
			BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
			writer.write(pokemon.toString() + "\n");
			writer.close();
		} catch (IOException e) {
			System.out.println("Could not perform write operation on " + file.getAbsolutePath() + "!");
			e.printStackTrace();
		}
	}
	
	public void clear()
	{
		try {
			BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
			writer.close();
		} catch (IOException e) {
			System.out.println("Could not perform write operation on " + file.getAbsolutePath() + "!");
			e.printStackTrace();
		}
	}
	
	public Pokemon readPokemon(String name)
	{
		Pokemon p = null;
		try {
			Scanner sc = new Scanner(file, "UTF-8");
			
			while(sc.hasNextLine())
			{
				p = new Pokemon(sc.nextLine());
				if (p.getName().toLowerCase().equals(name))
				{
					sc.close();
					return p;
				}
			}		
			sc.close();	
		} catch (FileNotFoundException e) {
			System.out.println(file.getAbsolutePath() + " does not exist for reading!");
			e.printStackTrace();
		}
		
		return null;
	}

	public Collection<Pokemon> readAllPokemon()
	{
		Collection<Pokemon> p = new ArrayList<Pokemon>();
		try {
			Scanner sc = new Scanner(file, "UTF-8");
			while(sc.hasNext())
			{
				p.add(new Pokemon(sc.nextLine()));
			}	
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println(file.getAbsolutePath() + " does not exist for reading!");
			e.printStackTrace();
		}	
	
		return p;	
	}
}
