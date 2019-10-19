package pokemon.exceptions;

public class NotInPartyException extends Exception {

	private static final long serialVersionUID = -3394350923415032383L;

	public NotInPartyException() {}
	
	public NotInPartyException(String msg)
	{
		super(msg);
	}
}
