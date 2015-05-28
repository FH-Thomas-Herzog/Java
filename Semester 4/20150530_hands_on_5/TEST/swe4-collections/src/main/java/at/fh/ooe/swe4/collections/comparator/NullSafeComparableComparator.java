package at.fh.ooe.swe4.collections.comparator;

import java.util.Comparator;

/**
 * This is a null safe ascending {@link Comparator} implementation for
 * {@link Comparable} types
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 27, 2015
 * @param <T>
 *            the {@link Comparable} type of the to compare objects
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class NullSafeComparableComparator<T extends Comparable> implements
		Comparator<T> {

	@Override
	public int compare(T o1, T o2) {
		// both are null
		if ((o1 == null) && (o2 == null)) {
			return 0;
		} else if (o1 == null) {
			return -1;
		} else if (o2 == null) {
			return 1;
		}
		return o1.compareTo(o2);
	}
}
