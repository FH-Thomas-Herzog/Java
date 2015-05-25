package at.fh.ooe.swe4.collections.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import at.fh.ooe.swe4.collections.api.Node;
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
public class NMKTreeTreeNode<T> implements Node<T>,
		Comparable<NMKTreeTreeNode<T>>, Iterable<NMKTreeTreeNode<T>> {

	/**
	 * Enumeration for representing the split type.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 23, 2015
	 */
	public static enum Split {
		HEAD, TAIL;
	}

	private NMKTreeTreeNode<T> parent;
	private final SortedSet<NMKTreeTreeNode<T>> children;
	private final SortedSet<T> keys;

	private final Comparator<T> comparator;

	/**
	 * Creates an empty node
	 */
	public NMKTreeTreeNode() {
		this(null, new TreeSet<NMKTreeTreeNode<T>>(), null);
	}

	/**
	 * Creates an node with one element set
	 * 
	 * @param element
	 *            the element to be set
	 */
	public NMKTreeTreeNode(final T element) {
		this(element, new TreeSet<NMKTreeTreeNode<T>>(
				new NullSafeComparableComparator<NMKTreeTreeNode<T>>()), null);
	}

	/**
	 * Creates an node with an custom {@link Comparator} instance
	 * 
	 * @param element
	 *            the element managed by this node
	 * @param comparator
	 *            the comparator used for sorting the managed keys and
	 *            referenced children
	 */
	public NMKTreeTreeNode(final T element, final Comparator<T> comparator) {
		this(element, new TreeSet<NMKTreeTreeNode<T>>(
				new NullSafeComparableComparator<NMKTreeTreeNode<T>>()),
				comparator);
	}

	/**
	 * Creates an fully customized node
	 * 
	 * @param key
	 *            the key for this node
	 * @param children
	 *            the children to be set on this node
	 * @param comparator
	 *            the comparator used for key sorting
	 * @throws NullPointerException
	 *             if the children set is null
	 */
	public NMKTreeTreeNode(final T element,
			final SortedSet<NMKTreeTreeNode<T>> children,
			final Comparator<T> comparator) {
		super();
		Objects.requireNonNull(children, "Children must be given");

		this.comparator = comparator;
		this.children = new TreeSet<>(
				new NullSafeComparableComparator<NMKTreeTreeNode<T>>());
		this.children.addAll(children);
		this.keys = new TreeSet<>(comparator);

		if (element != null) {
			addKey(element);
		}
	}

	/**
	 * Adds a child to this node
	 * 
	 * @param child
	 *            the child to be added
	 */
	public void addChild(final NMKTreeTreeNode<T> child) {
		child.setParent(this);
		children.add(child);
	}

	/**
	 * Removes the given child from the referenced children
	 * 
	 * @param node
	 *            the node to be removed
	 * @return true if the node could be removed, false otherwise
	 */
	public boolean removeChild(final NMKTreeTreeNode<T> node) {
		return children.remove(node);
	}

	/**
	 * Removes all references to all children.
	 */
	public void clearChildren() {
		children.clear();
	}

	/**
	 * Adds a key to this node
	 * 
	 * @param key
	 *            the key to be added
	 */
	public void addKey(final T key) {
		keys.add(key);
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
		return (getKeySize() == 0) ? null : keys.first();
	}

	/**
	 * Gets the highest key hold by this tree.
	 * 
	 * @return the highest key or null if no key is set.
	 */
	public T highestKey() {
		return (getKeySize() == 0) ? null : keys.last();
	}

	/**
	 * Gets the middle key and only if there are all possible keys set.
	 * 
	 * @return the middle key if all possible keys are set, null otherwise
	 */
	public T middleKey() {
		if (getKeySize() != 3) {
			return null;
		} else {
			final Iterator<T> it = keyIterator();
			it.next();
			return it.next();
		}
	}

	/**
	 * Gets and key at the given index.
	 * 
	 * @param _idx
	 *            the index where the value resides
	 * @return the found value
	 * @throws IndexOutOfBoundsException
	 *             if the index is invalid
	 */
	public T getKeyByIdx(final int _idx) {
		int idx = 0;
		if ((_idx >= 0) && (_idx < getKeySize())) {
			for (T value : keys) {
				if (idx == _idx) {
					return value;
				}
				idx++;
			}
		}
		throw new IndexOutOfBoundsException("Key with idx=" + idx
				+ " does not exist");
	}

	/**
	 * Gets the key hold by this node.
	 * 
	 * @param _key
	 *            the key to search in the key set
	 * @return the found key from the collection, null if given key is null or
	 *         node does not hold the given key.
	 */
	public T getKey(final T _key) {
		if (_key == null) {
			return _key;
		}
		final Iterator<T> it = keyIterator();
		T key = null;
		while ((it.hasNext()) && (!_key.equals(key))) {
			key = it.next();
		}
		return (_key.equals(key)) ? key : null;
	}

	/**
	 * Gets the highest child referenced by this tree.
	 * 
	 * @return the highest child or null if there is no next higher.
	 */
	public NMKTreeTreeNode<T> highestChild() {
		return (getChildrenSize() == 0) ? null : children.last();
	}

	/**
	 * Gets the lowest child referenced by this tree.
	 * 
	 * @return the lowest child or null if there is no next higher.
	 */
	public NMKTreeTreeNode<T> lowestChild() {
		return (getChildrenSize() == 0) ? null : children.first();
	}

	/**
	 * Splits the hold children set into two sets where the set gets split in
	 * half (integer division).
	 * 
	 * @return the map containing the head and tail set of the hold children set
	 */
	public Map<Split, SortedSet<NMKTreeTreeNode<T>>> splitChildren() {
		int counter = 1;
		final Map<Split, SortedSet<NMKTreeTreeNode<T>>> split = new HashMap<>();
		split.put(Split.HEAD, new TreeSet<NMKTreeTreeNode<T>>(
				new NullSafeComparableComparator<NMKTreeTreeNode<T>>()));
		split.put(Split.TAIL, new TreeSet<NMKTreeTreeNode<T>>(
				new NullSafeComparableComparator<NMKTreeTreeNode<T>>()));

		for (NMKTreeTreeNode<T> child : children) {
			if (counter <= ((int) (getChildrenSize() / 2))) {
				split.get(Split.HEAD).add(child);
			} else {
				split.get(Split.TAIL).add(child);
			}
			counter++;
		}

		return split;
	}

	/**
	 * The current size of the hold keys
	 * 
	 * @return the hole key size
	 */
	public int getKeySize() {
		return keys.size();
	}

	/**
	 * Gets the current referenced children size
	 * 
	 * @return the hold children size
	 */
	public int getChildrenSize() {
		return children.size();
	}

	/**
	 * Gets the referenced parent node
	 * 
	 * @return the parent node
	 */
	public NMKTreeTreeNode<T> getParent() {
		return parent;
	}

	/**
	 * Sets the parent which references this node
	 * 
	 * @param parent
	 *            the parent to be set on this node
	 */
	public void setParent(NMKTreeTreeNode<T> parent) {
		this.parent = parent;
	}

	/**
	 * Gets the iterator for the children referenced by this node.
	 * 
	 * @return the iterator of the node's children
	 */
	@Override
	public Iterator<NMKTreeTreeNode<T>> iterator() {
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
	 * Gets the iterator for the hold children
	 * 
	 * @return the children iterator
	 */
	public Iterator<NMKTreeTreeNode<T>> childrenIterator() {
		return children.iterator();
	}

	/**
	 * TreeNodes are sorted by their first hold element.
	 */
	@Override
	public int compareTo(NMKTreeTreeNode<T> o) {
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
			throw new IllegalStateException(
					"Managed Elements need to implement Comparable<T> interface if no Comparator<T> instance is provided");
		}
		// in case of provided comparator
		if (comparator != null) {
			return comparator.compare(this.lowestKey(), o.lowestKey());
		}
		return ((Comparable<T>) lowestKey()).compareTo(o.lowestKey());
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
		NMKTreeTreeNode<T> other = (NMKTreeTreeNode<T>) obj;
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
