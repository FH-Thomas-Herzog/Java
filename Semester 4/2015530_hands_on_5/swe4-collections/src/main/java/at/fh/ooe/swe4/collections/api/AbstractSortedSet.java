package at.fh.ooe.swe4.collections.api;

import java.util.Comparator;
import java.util.Iterator;

import at.fh.ooe.swe4.collections.comparator.NullSafeComparableComparator;

/**
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 17, 2015
 * @param <T>
 */
public abstract class AbstractSortedSet<T, M extends TreeNode> implements SortedSet<T> {

	protected final Comparator<T> comparator;
	protected M root = null;
	protected int size = 0;

	public AbstractSortedSet() {
		this(null);
	}

	public AbstractSortedSet(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(T el) {
		return (el != null) ? el.equals(get(el)) : Boolean.FALSE;
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

	protected void checkForValidElement(final T el) {
		if ((el != null) && (comparator == null) && (!(el instanceof Comparable))) {
			throw new IllegalStateException("If no Comparator<T> instance is provided then the managed Elements need to implement Comparable<T> interface");
		}
	}

	protected int compareElements(final T o1, final T o2) {
		checkForValidElement(o1);
		checkForValidElement(o2);
		if (comparator != null) {
			return comparator.compare(o1, o2);
		} else {
			return new NullSafeComparableComparator<Comparable>().compare(((Comparable<T>) o1), ((Comparable<T>) o2));
		}
	}
}
