package pokemon.exceptions;

public class PokemonNotFoundException extends Exception {

	private static final long serialVersionUID = 2955965223145392473L;
	
	public PokemonNotFoundException() {}
	
	public PokemonNotFoundException(String msg)
	{
		super(msg);
	}
}
