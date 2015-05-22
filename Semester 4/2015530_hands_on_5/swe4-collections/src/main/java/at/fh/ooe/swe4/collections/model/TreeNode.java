package at.fh.ooe.swe4.collections.model;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import at.fh.ooe.swe4.collections.comparator.NullSafeComparableComparator;

/**
 * This is the node used for a tree which is allowed to hold multiple keys and
 * children references.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 22, 2015
 * @param <T>
 *            the comparable type managed by this node
 */
public class TreeNode<T extends Comparable<T>> implements Comparable<TreeNode<T>>, Iterable<TreeNode<T>> {

	private TreeNode<T> parent;
	private final SortedSet<TreeNode<T>> children;
	private final SortedSet<T> keys;

	private final Comparator<T> comparator;
	private int keySize = 0;
	private int childrenSize = 0;

	/**
	 * Creates an empty node
	 */
	public TreeNode() {
		this(null);
	}

	/**
	 * Creates an node with one element set
	 * 
	 * @param element
	 *            the element to be set
	 */
	public TreeNode(final T element) {
		this(element, null, null);
	}

	/**
	 * Creates an node with an custom {@link Comparator} instance
	 * 
	 * @param element
	 *            the element managed by this node
	 * @param comparator
	 *            the comparator used for sorting the managed keys and
	 *            referenced children
	 * @throws NullPointerException
	 *             if comparator is null
	 */
	public TreeNode(final T element, final Comparator<T> comparator) {
		Objects.requireNonNull(comparator, "Cannot create node with null comparator");

		this.comparator = comparator;
		this.children = new TreeSet<>(new NullSafeComparableComparator<TreeNode<T>>());
		this.keys = new TreeSet<>(comparator);
	}

	/**
	 * Creates an fully customized node
	 * 
	 * @param children
	 *            the referenced children of this node
	 * @param keys
	 *            the managed keys of this node
	 */
	public TreeNode(final T element, final SortedSet<TreeNode<T>> children, final SortedSet<T> keys) {
		super();
		this.comparator = null;

		this.children = new TreeSet<>(new NullSafeComparableComparator<TreeNode<T>>());
		if (children != null) {
			this.children.addAll(children);
		}
		if (keys != null) {
			this.keys = keys;
		} else {
			this.keys = new TreeSet<>();
		}

		this.keys.add(element);
	}

	/**
	 * Adds a child to this node
	 * 
	 * @param child
	 *            the child to be added
	 * @throws IllegalStateException
	 *             if there are already 4 elements present
	 */
	public void addChild(final TreeNode<T> child) {
		if (childrenSize == 4) {
			throw new IllegalStateException("Only 4 children are allowed");
		}
		children.add(child);
		childrenSize++;
	}

	/**
	 * Adds a key to this node
	 * 
	 * @param key
	 *            the key to be added
	 * @throws IllegalStateException
	 *             if there are already 3 key present
	 */
	public void addKey(final T key) {
		if (keySize == 3) {
			throw new IllegalStateException("Only 3 keys are allowed to be hold");
		}
		keys.add(key);
		keySize++;
	}

	/**
	 * Removes an key from this node
	 * 
	 * @param key
	 *            the key to removed from this node
	 * @return true if the node has been removed, false otherwise
	 */
	public boolean removeKey(final T key) {
		return keys.remove(key);
	}

	/**
	 * Gets the lowest key hold by this tree.
	 * 
	 * @return the lowest key or null if no key is set.
	 */
	public T lowestKey() {
		return (keySize == 0) ? null : keys.first();
	}

	/**
	 * Gets the highest key hold by this tree.
	 * 
	 * @return the highest key or null if no key is set.
	 */
	public T highestKey() {
		return (keySize == 0) ? null : keys.last();
	}

	/**
	 * Gets the highest child referenced by this tree.
	 * 
	 * @return the highest child or null if there is no next higher.
	 */
	public TreeNode<T> highestChild() {
		return (childrenSize == 0) ? null : children.last();
	}

	/**
	 * Gets the lowest child referenced by this tree.
	 * 
	 * @return the lowest child or null if there is no next higher.
	 */
	public TreeNode<T> lowestChild() {
		return (childrenSize == 0) ? null : children.first();
	}

	/**
	 * The current size of the hold keys
	 * 
	 * @return the hole key size
	 */
	public int getKeySize() {
		return keySize;
	}

	/**
	 * Gets the current referenced children size
	 * 
	 * @return the hold children size
	 */
	public int getChildrenSize() {
		return childrenSize;
	}

	/**
	 * Gets the referenced parent node
	 * 
	 * @return the parent node
	 */
	public TreeNode<T> getParent() {
		return parent;
	}

	/**
	 * Sets the parent which references this node
	 * 
	 * @param parent
	 *            the parent to be set on this node
	 */
	public void setParent(TreeNode<T> parent) {
		this.parent = parent;
	}

	/**
	 * Gets the iterator for the children referenced by this node.
	 * 
	 * @return the iterator of the node's children
	 */
	@Override
	public Iterator<TreeNode<T>> iterator() {
		return children.iterator();
	}

	/**
	 * Gets the iterator for the hold keys.
	 * 
	 * @return the iterator for the old keys.
	 */
	public Iterator<T> keyIterator() {
		return keys.iterator();
	}

	/**
	 * TreeNodes are sorted by their first hold element.
	 */
	@Override
	public int compareTo(TreeNode<T> o) {
		// in case of provided comparator
		if (comparator != null) {
			return comparator.compare(this.lowestKey(), o.lowestKey());
		}
		// Needs Comparable interface implemented
		else {
			if ((lowestKey() == null) && (o.lowestKey() == null)) {
				return 0;
			}
			if (lowestKey() == null) {
				return -1;
			}
			if (o.lowestKey() == null) {
				return 1;
			}
			if (!(lowestKey() instanceof Comparable)) {
				throw new IllegalStateException("Managed Elements need to implement Comparable<T> interface if no Comparator<T> instance is provided");
			}
			return ((Comparable<T>) lowestKey()).compareTo(o.lowestKey());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keys == null) ? 0 : keys.hashCode());
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
		if (keys == null) {
			if (other.keys != null) {
				return false;
			}
		} else if (!keys.equals(other.keys)) {
			return false;
		}
		return true;
	}
}
