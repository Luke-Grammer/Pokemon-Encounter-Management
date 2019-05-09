package pokemon;

public class Pair<T, K extends Comparable<K>> implements Comparable<Pair<T, K>> {
    private T first;
    private K second;

    public Pair() {
	this.first = null;
	this.second = null;
    }

    public Pair(T first, K second) {
	this.first = first;
	this.second = second;
    }

    public T getFirst() {
	return first;
    }

    public void setFirst(T first) {
	this.first = first;
    }

    public K getSecond() {
	return second;
    }

    public void setSecond(K second) {
	this.second = second;
    }

    @Override
    public int compareTo(Pair<T, K> pair) {
	return this.getSecond().compareTo(pair.getSecond());
    }

}
