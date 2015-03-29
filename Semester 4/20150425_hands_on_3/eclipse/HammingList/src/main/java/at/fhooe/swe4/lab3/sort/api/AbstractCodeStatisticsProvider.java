package at.fhooe.swe4.lab3.sort.api;

public abstract class AbstractCodeStatisticsProvider implements CodeStatisticProvider {

	private int comparision = 0;
	private int iterations = 0;
	private int swaps = 0;
	private int indexing = 0;

	
	// Sorter statistics
	@Override
	public int getComparision() {
		return comparision;
	}

	@Override
	public int getIterations() {
		return iterations;
	}

	@Override
	public int getSwaps() {
		return swaps;
	}

	@Override
	public int getIndexing() {
		return indexing;
	}

}
