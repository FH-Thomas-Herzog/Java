package at.fhooe.swe4.lab3.sort.api;

public interface CodeStatisticProvider {

	/**
	 * @return the comparision count
	 */
	public int getComparision();

	/**
	 * @return the iteration count
	 */
	public int getIterations();

	/**
	 * @return the swap count
	 */
	public int getSwaps();

	/**
	 * @return the indexing count
	 */
	public int getIndexing();
}
