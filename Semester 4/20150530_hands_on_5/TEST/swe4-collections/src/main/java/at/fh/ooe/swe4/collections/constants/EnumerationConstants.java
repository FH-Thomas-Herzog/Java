package at.fh.ooe.swe4.collections.constants;

/**
 * This class holds the enumerations needed by the implementations.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 27, 2015
 */
public final class EnumerationConstants {

	/**
	 * Not meant to be instantiated
	 */
	private EnumerationConstants() {
		super();
	}

	/**
	 * Defines the boundary for a split set
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 27, 2015
	 */
	public static enum Boundary {
		LOWER, UPPER;
	}

	/**
	 * Defines the direction to go.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 27, 2015
	 */
	public static enum Direction {
		LEFT, RIGHT;
	}
}
