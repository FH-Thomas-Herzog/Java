package at.fhooe.swe4.lab3.hamming.list;

import java.util.HashMap;
import java.util.Map;

/**
 * http://www.solvium.de/programmierung/java/primfaktorzerlegung/
 * @author Thomas
 *
 */
public class Main {

	private static final int MAX_PRIM_FACT = 5;

	public static void main(String[] args) {
		long count = 288325195312500000L;
		// int idx = 1;
		// final int[] hamming = new int[3 * count];
		// hamming[0] = 1;
		// final long startMilis = System.currentTimeMillis();
		// for (int j = 1; j <= count; j++) {
		// // hamming[idx] = 2 * j;
		// // hamming[idx + 1] = 3 * j;
		// // hamming[idx + 2] = 5 * j;
		// System.out.println(new StringBuilder().append((2 * j)).toString());
		// System.out.println(new StringBuilder().append((3 * j)).toString());
		// System.out.println(new StringBuilder().append((5 * j)).toString());
		// idx += 3;
		// }
		// final long endMilis = System.currentTimeMillis();
		// System.out.println("timespned in milis: " + (endMilis - startMilis));

		final int[] allowedPrim = { 2, 3, 5 };
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(2, 0);
		map.put(3, 0);
		map.put(5, 0);
		while (count != 1) {
			for (int prim : allowedPrim) {
				if (count % prim == 0) {
					map.put(prim, map.get(prim) + 1);
					count = count / prim;
					System.out.println(count);
				}
			}
		}
		System.out.println("##############################");
//		long res = 1;
//		if (map.get(2) != 0) {
//			res *= Math.pow(2, map.get(2));
//		}
//		if (map.get(3) != 0) {
//			res *= Math.pow(3, map.get(3));
//		}
//		if (map.get(5) != 0) {
//			res *= Math.pow(5, map.get(5));
//		}
//		System.out.println("result prim multi: " + res);
		System.out.println(map.get(2));
		System.out.println(map.get(3));
		System.out.println(map.get(5));
	}

}
