package at.fhooe.swe4.lab3.hamming.list;

import java.util.HashMap;
import java.util.Map;

public class HammingList {

	private static final int[] ALLOWED_PRIM = { 2, 3, 5 };
	private final Map<Integer, Integer> primPotMap = new HashMap<Integer, Integer>();

	private long n = -1;

	/**
	 * Empty constructor
	 */
	public HammingList() {
		super();
		prepare(-1);
	}

	/**
	 * Prepares the instance by setting the proper states on the private
	 * members.
	 * 
	 * @param n
	 *            the number to calculate hamming list for. If n <= 0 then its
	 *            meant no number has been given. -1 will be the initial state
	 *            of variable n
	 */
	private void prepare(final long n) {
		for (int prim : ALLOWED_PRIM) {
			primPotMap.put(prim, 0);
		}
		this.n = n;
	}
	
	private void validateState() {
		if (this.n <= 0) {
			throw new IllegalArgumentException("n must be greather than 0");
		}
	}

	/**
	 * Inits the hamming list.
	 * 
	 * @param n
	 *            the number to calculate hammming list for. (n > 0)
	 * @return the current instance
	 * @throws IllegalArgumentException
	 *             if n <= 0
	 */
	public HammingList init(final long n) {
		prepare(n);
		validateState();
		return this;
	}

	/**
	 * Cleans the hamming list up by getting back to the current state. The next
	 * allowed method to be called is init(long n). If the hamming list has
	 * already been cleaned up then this method does nothing.
	 * 
	 * @return the current instance.
	 */
	public HammingList cleanup() {
		if (n > 0) {
			prepare(-1);
		}
		return this;
	}

	/**
	 * Prints the hamming list in the form of '2^i * 3^j * 5^k' depending if the
	 * prime is part of the calculated prime factors.
	 * 
	 * @return the current instance
	 * @throws Invalid
	 */
	public HammingList print() {
		validateState();
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ALLOWED_PRIM.length; i++) {
			final int prim = ALLOWED_PRIM[i];
			final int count = primPotMap.get(prim);
			if (count > 0) {
				sb.append(prim).append("^").append(count);
				if ((i + 1) < ALLOWED_PRIM.length) {
					sb.append(" * ");
				}
				sb.append(" = ").append(n);
			}
		}
		System.out.println(sb.toString());
		return this;
	}
}
