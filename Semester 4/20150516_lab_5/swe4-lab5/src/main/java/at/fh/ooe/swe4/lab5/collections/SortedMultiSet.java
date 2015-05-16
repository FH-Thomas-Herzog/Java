package at.fh.ooe.swe4.lab5.collections;

/**
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 16, 2015
 * @param <T>
 */
public interface SortedMultiSet<T extends Comparable<T>> extends Iterable<T> {

	void add(T el);

	boolean contains(T el);

	T get(T el);

	int size();
}
