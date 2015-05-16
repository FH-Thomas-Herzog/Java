package at.fh.ooe.swe4.collections.model;

import java.util.SortedSet;
import java.util.TreeSet;

public class TreeNode<T extends Comparable<T>> implements Comparable<TreeNode<T>> {

	private TreeNode<T> parent;
	private int size = 0;
	private final SortedSet<TreeNode<T>> children = new TreeSet<>();
	private final SortedSet<T> elements = new TreeSet<>();

	@Override
	public int compareTo(TreeNode<T> o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
