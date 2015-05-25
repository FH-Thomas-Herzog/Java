package at.fh.ooe.swe4.collections.impl;

import iterator.NMKTreeIterator;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.SortedSet;

import at.fh.ooe.swe4.collections.api.AbstractSortedSet;
import at.fh.ooe.swe4.collections.api.SortedTreeSet;
import at.fh.ooe.swe4.collections.model.NMKTreeTreeNode;
import at.fh.ooe.swe4.collections.model.NMKTreeTreeNode.Split;

/**
 * This is a tree implementation which uses a self balancing 2-3-4 tree for
 * managing the hold elements.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 16, 2015
 * @param <T>
 *            the managed key type
 */
public class TwoThreeFourTreeSet<T> extends
		AbstractSortedSet<T, NMKTreeTreeNode<T>> implements SortedTreeSet<T> {

	/**
	 * Enumeration which specifies which process shall be applied on the current
	 * metatdata instance.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 25, 2015
	 */
	private static enum ProcessState {
		NEXT_STEP, ADD_TO_CURRENT, ADD_TO_SPLIT, IS_CURRENT;
	}

	/**
	 * This class is used for holding the balance result and allows to pass
	 * through the actual height.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 25, 2015
	 * @param <T>
	 *            the managed key type
	 */
	private static class BalanceResult<T> {
		private int height;
		private NMKTreeTreeNode<T> node;

		/**
		 * Creates a result instance and sets current height
		 * 
		 * @param height
		 *            the height where the node resides
		 */
		public BalanceResult(final int height) {
			super();
			this.height = height;
		}

		/**
		 * Decrease height by one.
		 */
		public void dec() {
			height--;
		}

		/**
		 * Gets the resulting node
		 * 
		 * @return the resulting node
		 */
		public NMKTreeTreeNode<T> getNode() {
			return node;
		}

		/**
		 * Sets the current node. Should correspond tot he set height.
		 * 
		 * @param node
		 *            the node to be set
		 */
		public void setNode(NMKTreeTreeNode<T> node) {
			this.node = node;
		}

		/**
		 * Gets the height of the current node
		 * 
		 * @return the height of the hold node
		 */
		public int getHeight() {
			return height;
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
	 *            the managed key tpye
	 */
	public static class TreeNodeMetadata<T> {

		public final ProcessState process;
		public final NMKTreeTreeNode<T> current;
		public final NMKTreeTreeNode<T> toVisit;

		/**
		 * Creates a metadata instance with the current node set
		 * 
		 * @param current
		 *            the current visited node
		 * @throws NullPointerException
		 *             if the current node is null
		 */
		public TreeNodeMetadata(final NMKTreeTreeNode<T> current) {
			this(current, null, null);
		}

		/**
		 * Creates an instance with the current node and toVisit node.<br>
		 * A null toVisit indicates that the current node is processable.
		 * 
		 * @param current
		 *            the current visited node
		 * @param toVisit
		 *            the next to visit node
		 * @param process
		 *            TODO
		 * @throws NullPointerException
		 *             if the current node is null
		 */
		public TreeNodeMetadata(final NMKTreeTreeNode<T> current,
				final NMKTreeTreeNode<T> toVisit, ProcessState process) {
			super();
			Objects.requireNonNull(current,
					"At least current node must be given");

			this.current = current;
			this.toVisit = toVisit;
			this.process = process;
		}
	}

	private int height = 0;

	public TwoThreeFourTreeSet() {
		super();
	}

	public TwoThreeFourTreeSet(Comparator<T> comparator) {
		super(comparator);
	}

	@Override
	public Iterator<T> iterator() {
		return new NMKTreeIterator<T>(root);
	}

	@Override
	public boolean add(T e) {
		boolean modified = Boolean.FALSE;
		Integer currentHeight = Integer.valueOf(1);
		/*-- null not allowed --*/
		if (e != null) {
			/*-- no root present --*/
			if (root == null) {
				root = new NMKTreeTreeNode<T>(e, comparator());
				currentHeight = 1;
				modified = Boolean.TRUE;
			}
			/*-- search node and add key --*/
			else {
				NMKTreeTreeNode<T> node = root;
				while (node != null) {
					TreeNodeMetadata<T> metadata = calculateTreeNodeMetadata(
							node, e);
					switch (metadata.process) {
					// do nothing on duplicate
					case IS_CURRENT:
						node = null;
						break;
					// add to current node
					case ADD_TO_CURRENT:
						node.addKey(e);
						modified = Boolean.TRUE;
						node = null;
						break;
					// split node and walk to proper child
					case ADD_TO_SPLIT:
						// balance tree and revisit balanced tree
						final BalanceResult<T> result = new BalanceResult<T>(
								currentHeight);
						balanceTree(node, result);
						// get new current node
						node = result.getNode();
						// set the height of the new current node
						currentHeight = result.getHeight();
						break;
					// go to next node
					case NEXT_STEP:
						node = metadata.toVisit;
						currentHeight++;
						break;
					default:
						break;
					}
				}
			}
		}

		// Set new height if higher than actual set one
		height = (height < currentHeight) ? currentHeight : height;

		if (modified) {
			size++;
		}

		return modified;
	}

	@Override
	public T get(T el) {
		NMKTreeTreeNode<T> node = root;
		if (node == null) {
			return null;
		}
		while (node != null) {
			final TreeNodeMetadata<T> metadata = calculateTreeNodeMetadata(
					node, el);
			switch (metadata.process) {
			// found on this node
			case IS_CURRENT:
				return metadata.current.getKey(el);
				// need to visit next node
			case NEXT_STEP:
				node = metadata.toVisit;
				break;
			// could be placed here therefore no further node to visit
			case ADD_TO_CURRENT:
				return null;
				// would cause new node therefore node further node to visit
			case ADD_TO_SPLIT:
				return null;
			default:
				break;
			}
		}

		// Should never get here
		throw new IllegalStateException(
				"Should never get to end of get method ");
	}

	@Override
	public T first() {
		if (size() == 0) {
			throw new NoSuchElementException("Tree is empty");
		}
		NMKTreeTreeNode<T> node = root;
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
		NMKTreeTreeNode<T> node = root;
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
	private TreeNodeMetadata<T> calculateTreeNodeMetadata(
			final NMKTreeTreeNode<T> _node, final T _key) {
		TreeNodeMetadata<T> model = null;

		/*-- 1. one of last nodes --*/
		if (_node.getChildrenSize() == 0) {
			final ProcessState process;
			// node holds key
			if (_node.getKey(_key) != null) {
				process = ProcessState.IS_CURRENT;
			}
			// node is full
			else if (_node.getKeySize() == 3) {
				process = ProcessState.ADD_TO_SPLIT;
			}
			// able to add to current node
			else {
				process = ProcessState.ADD_TO_CURRENT;
			}
			model = new TreeNodeMetadata<T>(_node, null, process);
		}
		/* 2. find node to visit */
		else {
			final Iterator<NMKTreeTreeNode<T>> childrenIt = _node
					.childrenIterator();
			final Iterator<T> keyIt = _node.keyIterator();
			// visit all children along with keys
			while ((childrenIt.hasNext()) && (model == null)) {
				final NMKTreeTreeNode<T> node = childrenIt.next();
				// as long as keys are left we can go left depending current key
				if (keyIt.hasNext()) {
					final T key = keyIt.next();
					final int compResult = compareElements(_key, key);
					// we found a duplicate
					if (compResult == 0) {
						model = new TreeNodeMetadata<T>(_node, null,
								ProcessState.IS_CURRENT);
					}
					// is lower than current key, therefore visit child
					else if (compResult < 0) {
						model = new TreeNodeMetadata<T>(_node, node,
								ProcessState.NEXT_STEP);
					}
				}
				// here we have the highest node reached means go right
				else {
					model = new TreeNodeMetadata<T>(node, node,
							ProcessState.NEXT_STEP);
				}
			}
		}

		Objects.requireNonNull(model, "Model should be set here");

		return model;
	}

	/**
	 * Re-balances the tree if the current node has a keySize = 3.<br>
	 * It recalls itself recursively in case the parent has been modified and
	 * will perform a split as long as the current node overflows boundaries.
	 * 
	 * @param node
	 *            the node to be split
	 * @param currentLevels
	 *            TODO
	 * @throws NullPointerException
	 *             if the node is null
	 * @throws IllegalStateException
	 *             if the node has not exactly 0 or 4 children set
	 */
	private void balanceTree(final NMKTreeTreeNode<T> node,
			final BalanceResult<T> result) {
		Objects.requireNonNull(node, "The node to split must not be null");
		Objects.requireNonNull(result, "result instance must be given");

		// skip when less keys or null
		if (node.getKeySize() != 3) {
			result.setNode(node);
			return;
		}

		// valid nodes have zero or four children
		if ((node.getChildrenSize() != 0) && (node.getChildrenSize() != 4)) {
			throw new IllegalStateException(
					" with 3 keys 4 nodes must be present. size: "
							+ node.getChildrenSize());
		}

		NMKTreeTreeNode<T> parent, leftNode, rightNode;

		// 1. keys for split
		final T leftKey = node.lowestKey();
		final T middleKey = node.middleKey();
		final T rightKey = node.highestKey();
		// 2. involved nodes
		parent = node.getParent();
		leftNode = new NMKTreeTreeNode<T>(leftKey, comparator());
		rightNode = new NMKTreeTreeNode<T>(rightKey, comparator());
		// 3. split children if 4 children present
		if (node.getChildrenSize() == 4) {
			// split the children
			Map<Split, SortedSet<NMKTreeTreeNode<T>>> splitChildren = node
					.splitChildren();
			// iterate over head an tail subset
			for (Entry<Split, SortedSet<NMKTreeTreeNode<T>>> entry : splitChildren
					.entrySet()) {
				// Add each node to proper new node
				for (NMKTreeTreeNode<T> child : entry.getValue()) {
					switch (entry.getKey()) {
					case HEAD:
						leftNode.addChild(child);
						break;
					case TAIL:
						rightNode.addChild(child);
					default:
						break;
					}
				}
			}
			node.clearChildren();
		}
		// 4.1 add to parent if present
		if (parent != null) {
			parent.addKey(middleKey);
			parent.removeChild(node);
			parent.addChild(leftNode);
			parent.addChild(rightNode);
			// re-balance because overflow could occur
			result.dec();
			balanceTree(parent, result);
			return;
		}
		// 3.2 else modify current node
		else {
			node.removeKey(leftKey);
			node.removeKey(rightKey);
			node.addChild(leftNode);
			node.addChild(rightNode);
			result.setNode(node);
			return;
		}
	}
}
