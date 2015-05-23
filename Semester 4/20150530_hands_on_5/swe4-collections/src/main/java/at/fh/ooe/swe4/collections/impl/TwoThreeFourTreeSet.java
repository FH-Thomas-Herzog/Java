package at.fh.ooe.swe4.collections.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

import at.fh.ooe.swe4.collections.api.AbstractSortedSet;
import at.fh.ooe.swe4.collections.api.SortedTreeSet;
import at.fh.ooe.swe4.collections.model.TreeNode;
import at.fh.ooe.swe4.collections.model.TreeNode.Split;

/**
 * This is a tree implementation which uses a self balancing 2-3-4 tree for
 * managing the hold elements.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 16, 2015
 * @param <T>
 *            the {@link Comparable} type of the managed elements
 */
public class TwoThreeFourTreeSet<T extends Comparable<T>> extends AbstractSortedSet<T, TreeNode<T>> implements SortedTreeSet<T> {

	/**
	 * The iterator for the 2-3-4 tree.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 22, 2015
	 * @param <T>
	 *            the {@link Comparable} type managed by the backed tree
	 */
	public static class TwoThreeFourTreeSetIterator<T extends Comparable<T>> implements Iterator<T> {

		private Iterator<T> keyIterator = new TreeSet<T>().iterator();
		private final Stack<TreeNode<T>> unvisitedNodes = new Stack<>();

		/**
		 * Creates an constructor for the tree represented by the provided root
		 * node.
		 * 
		 * @param root
		 *            the root of the tree
		 */
		public TwoThreeFourTreeSetIterator(final TreeNode<T> root) {
			super();
			TreeNode<T> node = root;
			TreeNode<T> prev = null;
			while (node != null) {
				unvisitedNodes.push(node);
				prev = node;
				node = node.lowestChild();
				if (node == null) {
					this.keyIterator = prev.keyIterator();
				}
			}
		}

		@Override
		public boolean hasNext() {
			return (!unvisitedNodes.isEmpty()) || (keyIterator.hasNext());
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No further elements are available");
			}
			// elements are still left
			if (keyIterator.hasNext()) {
				return keyIterator.next();
			}
			// visit next node
			else {
				final TreeNode<T> current = unvisitedNodes.pop();
				TreeNode<T> node = current.lowestChild();
				while (node != null) {
					unvisitedNodes.push(node);
					node = node.lowestChild();
				}
				// get current key iterator
				this.keyIterator = current.keyIterator();
				// next key to provide
				return keyIterator.next();
			}
		}
	}

	/**
	 * This class is used for determining if the visited node is processable or
	 * not.<br>
	 * If the current node is not visible then the toVisit node should be set.
	 * It should never be possible that current node is never processable.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 23, 2015
	 * @param <T>
	 *            the {@link Comparable} type of the {@link TreeNode} managed
	 *            keys
	 */
	public static class TreeNodeMetadata<T extends Comparable<T>> {

		public final boolean currentProcessable;
		public final TreeNode<T> current;
		public final TreeNode<T> toVisit;

		/**
		 * Creates a metadata instance with the current node set
		 * 
		 * @param current
		 *            the current visited node
		 * @throws NullPointerException
		 *             if the current node is null
		 */
		public TreeNodeMetadata(final TreeNode<T> current) {
			this(current, null);
		}

		/**
		 * Creates an instance with the current node and toVisit node.<br>
		 * A null toVisit indicates that the current node is processable.
		 * 
		 * @param current
		 *            the current visited node
		 * @param toVisit
		 *            the next to visit node
		 * @throws NullPointerException
		 *             if the current node is null
		 */
		public TreeNodeMetadata(final TreeNode<T> current, final TreeNode<T> toVisit) {
			super();
			Objects.requireNonNull(current, "At least current node must be given");

			this.current = current;
			this.toVisit = toVisit;
			this.currentProcessable = (toVisit == null);
		}
	}

	private int height = 0;

	@Override
	public Iterator<T> iterator() {
		return new TwoThreeFourTreeSetIterator<T>(root);
	}

	@Override
	public boolean add(T e) {
		boolean modified = Boolean.FALSE;
		int currentHeight = 0;
		/*-- null not allowed --*/
		if (e != null) {
			/*-- no root present --*/
			if (root == null) {
				root = new TreeNode<T>(e, comparator());
				currentHeight = 1;
				modified = Boolean.TRUE;
			}
			/*-- search node and add key --*/
			else {
				TreeNode<T> node = root;
				while (node != null) {
					currentHeight++;
					final int oldKeySize = node.getKeySize();
					TreeNodeMetadata<T> metadata = calculateTreeNodeMetadata(root, e);
					// insert in current node
					if (metadata.currentProcessable) {
						// current contains less than 3 keys
						if (node.getKeySize() < 3) {
							node.addKey(e);
							// Only modified if no duplicate added
							modified = (oldKeySize == node.getKeySize()) ? Boolean.FALSE : Boolean.TRUE;
						}
						// node has already 3 keys, so add new child
						else {
							final TreeNode<T> tmp = new TreeNode<T>(e, comparator());
							node.addChild(tmp);
							node = tmp;
							modified = Boolean.TRUE;
						}
						break;
					}
					// go to next child
					node = metadata.toVisit;
				}

				/*-- balance if modified --*/
				if (modified) {
					balanceTree(node);
				}
			}
		}

		// Set new height if higher than actual set one
		height = (height < currentHeight) ? currentHeight : height;

		return modified;
	}

	@Override
	public T get(T el) {
		TreeNode<T> node = root;
		T result = null;
		while ((node != null) && (result == null)) {
			final TreeNodeMetadata<T> metadata = calculateTreeNodeMetadata(node, el);
			if (metadata.currentProcessable) {
				result = metadata.current.getKey(el);
			} else {
				node = metadata.toVisit;
			}
		}

		return result;
	}

	@Override
	public T first() {
		if (size() == 0) {
			throw new NoSuchElementException("Tree is empty");
		}
		TreeNode<T> node = root;
		while (node.lowestChild() != null) {
			node = node.lowestChild();
		}
		return node.lowestKey();
	}

	@Override
	public T last() {
		if (size() == 0) {
			throw new NoSuchElementException("Tree is empty");
		}
		TreeNode<T> node = root;
		while (node.highestChild() != null) {
			node = node.highestChild();
		}
		return node.highestKey();
	}

	@Override
	public int height() {
		return height;
	}

	// Private section
	/**
	 * Calculates the metadata for the current node, where the metadata instance
	 * contains the current node and toVisit node, which indicates that there is
	 * another node needed to be visited.<br>
	 * The calculation is performed by searching where the given key needs to be
	 * placed.<br>
	 * Be aware that the overflows are not considered here.
	 * 
	 * @param _node
	 *            the node to calculate metadata for
	 * @param _key
	 *            the key which gets included in the calculation
	 * @return
	 */
	private TreeNodeMetadata<T> calculateTreeNodeMetadata(final TreeNode<T> _node, final T _key) {
		final int resLowestKey = compareElements(_key, _node.lowestKey());
		final int resHighestKey = compareElements(_key, _node.highestKey());

		/* -- This node has no children -- */
		if (_node.getChildrenSize() == 0) {
			return new TreeNodeMetadata<T>(_node);
		}

		/* -- Children are present -- */
		final int resNextHighestKey = compareElements(_key, _node.lowestChild()
																	.lowestKey());

		// Key lower than lowest held key
		if (resLowestKey < 0) {
			return new TreeNodeMetadata<T>(_node.lowestChild());
		}
		// Key higher than highest held key
		if (resHighestKey > 0) {
			return new TreeNodeMetadata<T>(_node.highestChild());
		}
		// Key is insertable if next higher is lower than actual
		if ((resHighestKey <= 0) && (resNextHighestKey < 0)) {
			return new TreeNodeMetadata<T>(_node);
		}
		// Search for child to visit
		final Iterator<TreeNode<T>> it = _node.childrenIterator();
		TreeNode<T> prev = null;
		TreeNode<T> node = null;
		while (it.hasNext()) {
			node = it.next();
			final int resChildLowest = compareElements(node.lowestKey(), _key);
			final int resChildHighest = compareElements(node.highestKey(), _key);
			if ((resChildLowest >= 0) && (resChildHighest <= 0)) {
				return new TreeNodeMetadata<T>(_node, prev);
			}
			prev = node;
		}

		throw new IllegalStateException("Should found an result until here");
	}

	/**
	 * Performs the balancing of the tree if the current node needs to be split.
	 * 
	 * @param _node
	 *            the node to check if splitable
	 */
	private void balanceTree(final TreeNode<T> _node) {
		TreeNode<T> node = _node;

		/*-- Too much keys present but no children --*/
		if (node.getKeySize() == 3) {
			// handle modified node
			balanceTree(handleKeysSplit(node));
		}
		/*-- Too much children present. Split node --*/
		if (node.getChildrenSize() == 4) {
			// handle modified node
			balanceTree(handleChildrenSplit(node));
		}
	}

	/**
	 * Handles the keys split.
	 * 
	 * @param node
	 *            the node to be split
	 * @return the modified node
	 * @throws NullPointerException
	 *             if the node is null
	 * @throws IllegalStateException
	 *             if the node has not exactly 3 keys set
	 */
	private TreeNode<T> handleKeysSplit(final TreeNode<T> node) {
		Objects.requireNonNull(node, "The node to split must not be null");
		if (node.getKeySize() != 4) {
			throw new IllegalStateException("Node must have exactly 3 keys");
		}

		TreeNode<T> leftNode, rightNode;

		// 1. keys for new nodes
		final T leftKey = node.lowestKey();
		final T rightKey = node.highestKey();
		// 2. create new nodes
		leftNode = new TreeNode<T>(leftKey, comparator());
		rightNode = new TreeNode<T>(rightKey, comparator());
		// 3. remove keys from node
		node.removeKey(leftKey);
		node.removeKey(rightKey);
		// 4. add new children
		node.addChild(leftNode);
		node.addChild(rightNode);

		return node;
	}

	/**
	 * Performs an children split.
	 * 
	 * @param node
	 *            the node to split its children
	 * @return the modified parent
	 * @throws NullPointerException
	 *             if the given node is null
	 * @throws IllegalStateException
	 *             if the node has not exactly 4 children set
	 */
	private TreeNode<T> handleChildrenSplit(final TreeNode<T> node) {
		Objects.requireNonNull(node, "The node to split must not be null");
		if (node.getChildrenSize() != 4) {
			throw new IllegalStateException("Node must have exactly 4 children");
		}

		TreeNode<T> leftNode, rightNode, parent;

		parent = node.getParent();
		assert (node.getKeySize() == 3);

		// 1. Set middle key on parent
		parent.addKey(node.getKeyByIdx(1));
		// 2. split node children
		final Map<Split, SortedSet<TreeNode<T>>> splitChildren = node.splitChildrenByIdx(1);
		// 3. create new nodes
		leftNode = new TreeNode<T>(node.lowestKey(), splitChildren.get(Split.HEAD), comparator());
		rightNode = new TreeNode<T>(node.highestKey(), splitChildren.get(Split.TAIL), comparator());
		// 4. remove split node
		parent.removeChild(node);
		// 5. add new nodes
		parent.addChild(leftNode);
		parent.addChild(rightNode);

		return parent;
	}
}
