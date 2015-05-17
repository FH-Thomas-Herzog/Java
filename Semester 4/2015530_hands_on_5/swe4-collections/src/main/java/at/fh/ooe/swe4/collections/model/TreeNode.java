package at.fh.ooe.swe4.collections.model;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import at.fh.ooe.swe4.collections.comparator.NullSafeComparableComparator;

public class TreeNode<T> implements Comparable<TreeNode<T>>, Iterable<TreeNode<T>> {

	public final TreeNode<T> parent;
	public final SortedSet<TreeNode<T>> children = new TreeSet<>(new NullSafeComparableComparator<TreeNode<T>>());
	public final SortedSet<T> elements;

	private final Comparator<T> comparator;
	private int childrenSize = 0;
	private int elementSize = 0;

	public TreeNode() {
		this(null);
	}

	public TreeNode(final T element) {
		this(element, null, null);
	}

	public TreeNode(final T element, final TreeNode<T> parent, final Comparator<T> comparator) {
		super();
		this.parent = parent;
		this.elements.add(element);
		this.comparator = comparator;
		if (comparator != null) {
			this.elements = new TreeSet<>(comparator);
		} else {
			this.elements = new TreeSet<>();
		}
	}

	public void addChild(final TreeNode<T> child) {
		if (childrenSize == 4) {
			throw new IllegalStateException("Only 4 children are allowed");
		}
		children.add(child);
		childrenSize++;
	}

	public void addElement(final T element) {
		if (childrenSize == 3) {
			throw new IllegalStateException("Only 3 elements are allowed to be hold");
		}
		elements.add(element);
		childrenSize++;
	}

	public int getChildrenSize() {
		return childrenSize;
	}

	public int getElementSize() {
		return elementSize;
	}

	@Override
	public Iterator<TreeNode<T>> iterator() {
		return children.iterator();
	}

	/**
	 * TreeNodes are sorted by their first hold element.
	 */
	@Override
	public int compareTo(TreeNode<T> o) {
		// in case of provided comparator
		if (comparator != null) {
			return comparator.compare(this.elements.first(), o.elements.first());
		}
		// Needs Comparable interface implemented
		else {
			if ((elements.first() == null) && (o.elements.first() == null)) {
				return 0;
			}
			if (elements.first() == null) {
				return -1;
			}
			if (o.elements.first() == null) {
				return 1;
			}
			if (!(elements.first() instanceof Comparable)) {
				throw new IllegalStateException("Managed Elements need to implement Comparable<T> interface if no Comparator<T> instance is provided");
			}
			return ((Comparable<T>) elements.first()).compareTo(o.elements.first());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TreeNode<T> other = (TreeNode<T>) obj;
		if (elements == null) {
			if (other.elements != null) {
				return false;
			}
		} else if (!elements.equals(other.elements)) {
			return false;
		}
		return true;
	}
}
