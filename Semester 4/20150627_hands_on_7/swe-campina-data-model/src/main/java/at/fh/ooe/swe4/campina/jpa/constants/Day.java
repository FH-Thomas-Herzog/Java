package at.fh.ooe.swe4.campina.jpa.constants;

import java.util.Objects;

/**
 * Enumeration which specifies the available days.
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public enum Day {
	MONDAY("Montag"),
	THUESDAY("Dienstag"),
	WEDNESDAY("Mittwoch"),
	THURSDAY("Donnerstag"),
	FRIDAY("Freitag"),
	SATARDAY("Samstag"),
	SUNDAY("Sontag");

	public final String	label;

	private Day(String label) {
		this.label = label;
	}

	/**
	 * Resolve {@link Day} by its held label.
	 * 
	 * @param label
	 *            the label of the enumeration
	 * @return the found {@link Day} instance
	 * @throws NullPointerException
	 *             if the label is null
	 */
	public static final Day fromLabel(final String label) {
		Objects.requireNonNull(label);

		for (Day day : Day.values()) {
			if (day.label.equals(label)) {
				return day;
			}
		}
		throw new IllegalArgumentException("No day with label '" + label + "' found");
	}
}
