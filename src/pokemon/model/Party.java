package pokemon.model;

import java.util.Collection;

public interface Party {

	public Collection<Pokemon> getPartyMembers();
	public int size();
}
