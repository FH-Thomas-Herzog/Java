package at.fhooe.swe4.lab3.sort.api;

import java.util.Collection;
import java.util.List;

import at.fhooe.swe4.lab3.stat.api.StatisticsProvider;

/**
 * This interface specifies the heap functionalities.
 * 
 * @author Thomas Herzog
 *
 * @param <V>
 *            the value type of the elements in the heap
 */
public interface Heap<V extends Comparable<V>> {

	/**
	 * This enumeration specifies the supported heap types
	 * 
	 * @author Thomas Herog
	 *
	 */
	public static enum HeapType {
		/**
		 * WIll result an ascending ordered heap
		 */
		MAX_HEAP, /**
		 * WIll result an descending ordered heap
		 */
		MIN_HEAP;

		/**
		 * Compares the two comparable instances.
		 * <ul>
		 * <li>
		 * {@link HeapType#MIN_HEAP} performs an x < 0 comparision</li>
		 * <li>{@link HeapType#MIN_HEAP} performs an x > 0 comparision</li>
		 * </ul>
		 * 
		 * @param left
		 *            the instance which invokes the comparesTo method
		 * @param right
		 *            the parameter for lefts compareTomethod invocation
		 * @return the proper result for the specified heap type
		 */
		public <T extends Comparable<T>> boolean compare(T left, T right) {
			switch (this) {
			case MAX_HEAP:
				return left.compareTo(right) < 0;
			case MIN_HEAP:
				return left.compareTo(right) > 0;
			default:
				throw new IllegalStateException("This enum is not handled here but should. enum=" + this.name());
			}
		}
	}

	/**
	 * Initializes this heap with the given array of elements.
	 * 
	 * @param originalArrayValues
	 *            the values to build an heap structure from
	 * @param sortType
	 *            the type of how the elements should be
	 */
	public void init(V[] originalArrayValues, HeapType sortType);

	/**
	 * Initializes this heap with the given collection which provides the
	 * elements.
	 * 
	 * @param originalArrayValues
	 *            the values to build an heap structure from
	 * @param sortType
	 *            the type of how the elements should be
	 */
	public void init(Collection<V> originalIterableValues, HeapType sortType);

	/**
	 * Puts an element on the heap and keeps heap type specified order.
	 * 
	 * @param value
	 *            the element to be put on the heap
	 */
	public void enqueue(V value);

	/**
	 * Gets the top element of the heap
	 * 
	 * @return the top element
	 */
	public V dequeue();

	/**
	 * Converts the heap to a flat list which represents the backed tree
	 * structure.
	 * 
	 * @return the list representing the heap. Will be a new instance
	 */
	public List<V> toList();

	/**
	 * Converts the heap to an flat array which represents the bakced trees
	 * structure
	 * 
	 * @return the array representing the heap
	 */
	public V[] toArray();

	/**
	 * Answers the question if the heap has another element
	 * 
	 * @return true if there is still an element left on the heap
	 */
	public boolean hasNext();

	/**
	 * Returns the current size of the heap.
	 * 
	 * @return the heap element size
	 */
	public int size();

	/**
	 * Gets the statistics of the current instance
	 * 
	 * @return the current statistics
	 */
	public StatisticsProvider getStatisitcs();
}