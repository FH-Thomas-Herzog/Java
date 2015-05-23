package at.fh.ooe.swe4.collections.api;

/**
 * This interface specifies a sorted tree set.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 17, 2015
 * @param <T>
 *            the generic type of the managed elements
 */
public interface SortedTreeSet<T> extends SortedSet<T> {

	/**
	 * Returns the maximum height the backed sorted tree has. The height is
	 * equal to the number of levels
	 * 
	 * @return the height of the {@link SortedTreeSet} implementation backed
	 *         tree
	 */
	public int height();
}
