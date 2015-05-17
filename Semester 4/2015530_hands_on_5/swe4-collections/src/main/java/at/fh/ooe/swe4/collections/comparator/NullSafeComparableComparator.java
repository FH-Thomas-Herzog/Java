package at.fh.ooe.swe4.collections.comparator;

import java.util.Comparator;

public class NullSafeComparableComparator<T extends Comparable> implements Comparator<T> {

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
