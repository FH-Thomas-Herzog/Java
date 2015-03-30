package at.fhooe.swe4.lab3.stat;

import org.apache.commons.lang3.StringUtils;

/**
 * This is model which holds the code statistic data.
 * 
 * @author Thomas Herzog
 *
 */
public class CodeStatistics {

	private int comparision;
	private int iterations;
	private int swaps;
	private int indexing;

	private final String key;

	/**
	 * 
	 * @param key
	 *            hte key for this statistics data.
	 */
	public CodeStatistics(final String key) {
		super();
		if (StringUtils.isEmpty(key)) {
			throw new IllegalArgumentException("Statistic instance must be identified by an unique string key");
		}
		this.key = key.trim().toLowerCase();
		clear();
	}

	public String getKey() {
		return key;
	}

	public CodeStatistics incIf() {
		comparision++;
		return this;
	}

	public CodeStatistics decIf() {
		comparision--;
		return this;
	}

	public CodeStatistics incIt() {
		iterations++;
		return this;
	}

	public CodeStatistics decIt() {
		iterations--;
		return this;
	}

	public CodeStatistics incSwap() {
		swaps++;
		return this;
	}

	public CodeStatistics decSwap() {
		swaps--;
		return this;
	}

	public CodeStatistics incIdx() {
		indexing++;
		return this;
	}

	public CodeStatistics decIdx() {
		indexing--;
		return this;
	}

	public CodeStatistics clear() {
		comparision = 0;
		indexing = 0;
		iterations = 0;
		swaps = 0;
		return this;
	}

	// Sorter statistics

	public int getComparision() {
		return comparision;
	}

	public int getIterations() {
		return iterations;
	}

	public int getSwaps() {
		return swaps;
	}

	public int getIndexing() {
		return indexing;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CodeStatistics other = (CodeStatistics) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final String ln = System.getProperty("line.separator");
		final StringBuilder sb = new StringBuilder();
		sb.append("## statistic-key: ").append(key).append(ln);
		sb.append(String.format("## %15s %15s %15s %15s", "iterations", "comparisions", "indexing", "swaps")).append(ln);
		sb.append(String.format("## %15s %15s %15s %15s", iterations, comparision, indexing, swaps));
		return sb.toString();
	}
}
