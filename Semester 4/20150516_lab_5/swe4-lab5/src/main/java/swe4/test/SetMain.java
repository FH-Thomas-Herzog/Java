package swe4.test;

// class Person for test purposes only
// -----------------------------------

import java.util.Iterator;

import at.fh.ooe.swe4.lab5.collections.BstMultiSet;
import at.fh.ooe.swe4.lab5.collections.SortedMultiSet;

class Person implements Comparable<Person> {

	private String firstName, lastName;
	private int age;

	public Person(String firstName, String lastName, int age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	} // Person

	@Override
	public String toString() {
		return String.format("%s %s (%d)", firstName, lastName, age);
	} // toString

	// compareTo should be consistent with equals
	@Override
	public boolean equals(Object o) {
		return compareTo((Person) o) == 0;
	}

	public int compareTo(Person other) {
		int cmp = lastName.compareTo(other.lastName);
		if ((cmp < 0) || (cmp > 0))
			return cmp;
		else
			// if lastName is the same
			return firstName.compareTo(other.firstName);
	} // compareTo
} // Person

public class SetMain {

	public static void main(String[] argv) {

		System.out.println("------------ SortedMultiSet<String> --------------");

		SortedMultiSet<String> strSet = new BstMultiSet<>();
		strSet.add("B");
		strSet.add("A");
		strSet.add("C");
		strSet.add("C");
		// strSet.add(2); // results in a syntax error
		System.out.printf("strSet = %s%n", strSet);
		System.out.printf("strSet.get(\"A\") -> %s%n", strSet.get("A"));
		System.out.printf("strSet.get(\"X\") -> %s%n", strSet.get("X"));
		System.out.printf("strSet.contains(\"A\") -> %b%n", strSet.contains("A"));
		System.out.printf("strSet.contains(\"X\") -> %b%n", strSet.contains("X"));

		System.out.println("enumerate elements of strSet:");
		Iterator<String> strIt = strSet.iterator();
		while (strIt.hasNext())
			System.out.print(strIt.next() + " ");
		System.out.println();

		System.out.println("------------ SortedMultiSet<Integer> --------------");

		SortedMultiSet<Integer> intSet = new BstMultiSet<>();
		intSet.add(2); // --> automatic boxing: intSet.add(new Integer(2));
		intSet.add(1);
		intSet.add(3);
		intSet.add(10);
		intSet.add(5);

		@SuppressWarnings("unused")
		int elem = intSet.get(2); // automatic unboxing: Integer -> int
		System.out.printf("intSet = %s%n", intSet);

		System.out.println("enumerate elements using iterator interface:");
		Iterator<Integer> intIt = intSet.iterator();
		while (intIt.hasNext())
			System.out.print(intIt.next() + " ");
		System.out.println();

		System.out.println("enumerate elements using forEach method:");
		// Requires implementation of interface Iterable.
		// Iterator interface provides default implementation of forEach method.
		intSet.forEach(i -> System.out.print(i + " "));
		System.out.println();

		// Requires implementation of interface Iterable.
		System.out.println("enumerate elements using for loop:");
		for (int i : intSet)
			System.out.print(i + " ");
		System.out.println();

		System.out.println();
		System.out.printf("intSet.contains(\"5\") -> %b%n", intSet.contains(5));
		System.out.printf("intSet.contains(\"99\") -> %b%n", intSet.contains(88));

		System.out.println("------------ SortedMultiSet<Person> --------------");

		SortedMultiSet<Person> persSet = new BstMultiSet<>();
		persSet.add(new Person("Susi", "Wallner", 30));
		persSet.add(new Person("Franz", "Huber", 20));
		persSet.add(new Person("Hubert", "Wallner", 35));
		System.out.printf("persSet = %s%n", persSet);
	} // main
} // SetMain
