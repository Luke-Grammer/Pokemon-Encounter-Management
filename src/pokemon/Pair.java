package pokemon;

/**
 * A basic pair class. Contains two data members that can be accessed, modified,
 * and sorted.
 * 
 * @author Luke Grammer
 *
 * @param <T> is the first element in the pair.
 * @param <K> is the second element in the pair, must be comparable to itself.
 */
public class Pair<T, K extends Comparable<K>> implements Comparable<Pair<T, K>> {
	private T first;
	private K second;

	/**
	 * Default constructor, initializes all data members to null values.
	 */
	public Pair()
	{
		this.first = null;
		this.second = null;
	}

	/**
	 * Parameterized constructor that initializes the data members from the given arguments.
	 * 
	 * @param first is the first data element.
	 * @param second is the second data element.
	 */
	public Pair(T first, K second)
	{
		this.first = first;
		this.second = second;
	}

	/**
	 * Retrieves the first element in the pair.
	 * 
	 * @return the first element.
	 */
	public T getFirst()
	{
		return first;
	}

	/**
	 * Sets the first element in the pair to the value given as an argument.
	 * 
	 * @param first is the value to give the first element in the pair.
	 */
	public void setFirst(T first)
	{
		this.first = first;
	}

	/**
	 * Retrieves the second element in the pair.
	 * 
	 * @return the second element.
	 */
	public K getSecond()
	{
		return second;
	}

	/**
	 * Sets the second element in the pair to the value given as an argument.
	 * 
	 * @param second is the value to give the second element in the pair.
	 */
	public void setSecond(K second)
	{
		this.second = second;
	}

	@Override
	public int compareTo(Pair<T, K> pair)
	{
		return this.getSecond().compareTo(pair.getSecond());
	}

}
