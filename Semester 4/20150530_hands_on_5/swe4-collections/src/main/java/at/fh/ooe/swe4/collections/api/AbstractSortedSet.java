package at.fh.ooe.swe4.collections.api;

import java.lang.reflect.ParameterizedType;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

import at.fh.ooe.swe4.collections.comparator.NullSafeComparableComparator;

/**
 * The base class for the {@link SortedSet} implementing classes.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 17, 2015
 * @param <T>
 *            the type of the managed node values
 */
public abstract class AbstractSortedSet<T, M extends Node<T>> implements SortedSet<T> {

	protected final Comparator<T> comparator;
	protected M root = null;
	protected int size = 0;

	/**
	 * Default constructor where the tree uses natural ordering and assumes that
	 * Tree nodes are Comparable instances.
	 */
	public AbstractSortedSet() {
		super();
		this.comparator = null;
	}

	/**
	 * Sets an comparator which is used for sorting the tree managed elements.
	 * The managed instance does not need to be comparable instances.
	 * 
	 * @param comparator
	 *            the comparator to use for the sorting of this tree
	 * @throws NullPointerException
	 *             if the provided comparator is null
	 */
	public AbstractSortedSet(Comparator<T> comparator) {
		super();
		Objects.requireNonNull(comparator, "Cannot create tree with null comparator");

		this.comparator = comparator;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(T el) {
		return (get(el) != null) ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public T get(T el) {
		// null or empty tree
		if ((el == null) || (root == null)) {
			return null;
		}
		// Iterate over tree
		final Iterator<T> it = iterator();
		T value = null;
		while ((it.hasNext()) && (value == null)) {
			final T itValue = it.next();
			if (el.equals(itValue)) {
				value = itValue;
			}
		}
		return value;
	}

	@Override
	public Comparator<T> comparator() {
		return comparator;
	}

	@Override
	public T[] toArray(T[] array) {
		if ((array == null) || (array.length == 0)) {
			return array;
		}
		final Iterator<T> it = iterator();
		int idx = 0;
		while ((it.hasNext()) && (idx < array.length)) {
			array[idx] = it.next();
			idx++;
		}
		return array;
	}

	/**
	 * Compares the two given elements either with the set comparator or with
	 * natural ordering .
	 * 
	 * @param o1
	 *            the first instance
	 * @param o2
	 *            the second instance
	 * @return -1 if o1 is lower than o2<br>
	 *         0 if o1 and o2 are equal <br>
	 *         1 if o1 is greater than o2
	 */
	protected int compareElements(final T o1, final T o2) {
		if (comparator != null) {
			return comparator.compare(o1, o2);
		} else {
			return new NullSafeComparableComparator<Comparable<T>>().compare(((Comparable<T>) o1), ((Comparable<T>) o2));
		}
	}
}
