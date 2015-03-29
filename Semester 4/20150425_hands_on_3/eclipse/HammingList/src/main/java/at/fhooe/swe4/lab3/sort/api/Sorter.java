package at.fhooe.swe4.lab3.sort.api;

import java.util.List;

public interface Sorter<V extends Comparable<V>> {

	public V[] sortArray();

	public List<V> sortList();

	public V[] reverseSortArray();

	public List<V> reversSortList();
}
