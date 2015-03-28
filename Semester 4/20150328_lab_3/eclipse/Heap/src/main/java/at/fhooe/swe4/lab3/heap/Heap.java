package at.fhooe.swe4.lab3.heap;

import java.util.ArrayList;
import java.util.List;

public class Heap<V extends Comparable<V>> {
	private List<V> values;

	public Heap() {
		values = new ArrayList<V>();
	}

	public boolean less(final V a, final V b) {
		return a.compareTo(b) < 0;
	}

	public boolean isEmpty() {
		return values.isEmpty();
	}

	public void enqueue(final V a) {
		assert isHeap();
		values.add(a);
		upHeap();
	}

	public V dequeue() {
		assert isHeap();
		if (isEmpty()) {
			throw new IllegalStateException("Cannot perform dequeue on an empty heap");
		}
		V peak = peak();
		values.set(0, values.get(values.size() - 1));
		if (!isEmpty()) {
			downHeap();
		}
		values.remove(values.size() - 1);
		assert isHeap();
		return peak;
	}

	public void upHeap() {
		assert !isEmpty();
		int i = values.size() - 1;
		V tmp = values.get(i);
		while ((i != 0) && (less(values.get(parent(i)), tmp))) {
			values.set(i, values.get(parent(i)));
			i = parent(i);
		}
		values.set(i, tmp);
	}

	public void downHeap() {
		assert !isEmpty();
		int i = 0;
		V tmp = peak();
		while (left(i) < values.size()) {
			int j = left(i);
			if ((right(j) < values.size()) && (less(values.get(left(i)), values.get(right(i))))) {
				j = right(i);
			}
			if (!less(tmp, values.get(j))) {
				break;
			}
			values.set(i, values.get(j));
			i = j;
		}
		values.set(i,  tmp);
	}

	public boolean isHeap() {
		int i = 1;
		while ((i < values.size()) && (!less(values.get(parent(i)), values.get(i)))) {
			i++;
		}
		return i >= values.size();
	}

	public V peak() {
		return (!isEmpty()) ? values.get(0) : null;
	}

	private static int parent(final int i) {
		return (i - 1) / 2;
	}

	private static int left(final int i) {
		return (i * 2) + 1;
	}

	private static int right(final int i) {
		return (i * 2) + 2;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName()).append("[size=").append((values != null) ? values.size() : -1).append("] values[");
		for (V v : values) {
			sb.append(v.toString());
		}
		sb.append("]");
		return sb.toString();
	}
}
