package at.fhooe.swe4.lab3.sort.api;

public abstract class AbstractSorter<V extends Comparable<V>, C extends Iterable<V>> implements SorterStatistics, Sorter<V> {

	private int comparision = 0;
	private int iterations = 0;
	private int swaps = 0;
	private int indexing = 0;
	protected C container;

	public AbstractSorter(C container) {
		super();
		if (container == null) {
			throw new IllegalArgumentException("Container must not be null");
		}
		this.container = container;
	}

	// abstract methods
	/**
	 * Transforms the array values to the backed container structure
	 * 
	 * @param originalArrayValues
	 *            the array values to be transformed
	 */
	protected abstract void transform(final V[] originalArrayValues);

	/**
	 * Transforms the Iterable of values to the backed container structure
	 * 
	 * @param originalIterableValues
	 *            the iterable values to be transformed
	 */
	protected abstract void transform(final Iterable<V> originalArrayValues);

	// Sorter statistics
	@Override
	public int getComparision() {
		return comparision;
	}

	@Override
	public int getIterations() {
		return iterations;
	}

	@Override
	public int getSwaps() {
		return swaps;
	}

	@Override
	public int getIndexing() {
		return indexing;
	}

}
