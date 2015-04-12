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
	private int swaps;

	private final String key;

	/**
	 * 
	 * @param key
	 *            the key for this statistics data.
	 * @throws IllegalArgumentException
	 *             if the key is either null or an empty string
	 */
	public CodeStatistics(final String key) {
		super();
		if (StringUtils.isEmpty(key)) {
			throw new IllegalArgumentException("Statistic instance must be identified by an unique string key");
		}
		this.key = key.trim().toLowerCase();
		clear();
	}

	/**
	 * The key of this instance
	 * 
	 * @return the instance key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Increase the comparison counter.
	 * 
	 * @return the current instance
	 */
	public CodeStatistics incIf() {
		comparision++;
		return this;
	}

	/**
	 * Increases the swap counter
	 * 
	 * @return the current instance
	 */
	public CodeStatistics incSwap() {
		swaps++;
		return this;
	}

	/**
	 * Clears the code statistic by setting all counters to '0'
	 * 
	 * @return the current instance
	 */
	public CodeStatistics clear() {
		comparision = 0;
		swaps = 0;
		return this;
	}

	// Sorter statistics
	/**
	 * @return the comparison counter
	 */
	public int getComparision() {
		return comparision;
	}

	/**
	 * @return the swap counter
	 */
	public int getSwaps() {
		return swaps;
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
		sb.append(String.format("## %15s %15s", "comparisions", "swaps")).append(ln);
		sb.append(String.format("## %15s %15s", comparision, swaps));
		return sb.toString();
	}
}
