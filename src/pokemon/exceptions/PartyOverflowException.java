package pokemon.exceptions;

public class PartyOverflowException extends Exception{

	private static final long serialVersionUID = -364999959503732054L;
	
	public PartyOverflowException() {}
	
	public PartyOverflowException(String msg)
	{
		super(msg);
	}
}
