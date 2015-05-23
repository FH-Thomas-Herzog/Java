package at.fh.ooe.swe4.collections.api;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * This interface is the specification for a sorted set.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 17, 2015
 * @param <T>
 *            the generic type of the managed elements in this sorted set
 */
public interface SortedSet<T> extends Iterable<T> {

	/**
	 * Adds the element to the tree.
	 * 
	 * @param el
	 *            the element to be added.
	 * @return true if the elements was successfully added to the tree, false
	 *         otherwise
	 */
	public boolean add(T el);

	/**
	 * Gets the element from the tree.
	 * 
	 * @param el
	 *            the element to be get form the tree
	 * @return the found element null otherwise.
	 */
	public T get(T el);

	/**
	 * Answers the question of the given element is managed by this tree.
	 * 
	 * @param el
	 *            the element to be searched in the tree
	 * @return true if the element is managed by this tree, false otherwise
	 */
	public boolean contains(T el);

	/**
	 * The current size of the tree. The size is equal to the managed elements
	 * in this tree
	 * 
	 * @return the tree size
	 */
	public int size();

	/**
	 * The first element in this tree which will be the element with the lowest
	 * value.
	 * 
	 * @return the first element in this tree
	 * @throws NoSuchElementException
	 *             if the tree is empty.
	 */
	public T first();

	/**
	 * The last element in this tree which will be the element with the highest
	 * value.
	 * 
	 * @return the last element in this tree
	 * @throws NoSuchElementException
	 *             if the tree is empty.
	 */
	public T last();

	/**
	 * Gets the backed {@link Comparator} instance of null if natural oder is
	 * used for the tree
	 * 
	 * @return the backed {@link Comparator} instance
	 */
	public Comparator<T> comparator();

	/**
	 * Returns the elements managed by this tree represented by an array.
	 * 
	 * @param array
	 *            the array to be filled
	 * @return the filled array
	 */
	public T[] toArray(T[] array);
}
