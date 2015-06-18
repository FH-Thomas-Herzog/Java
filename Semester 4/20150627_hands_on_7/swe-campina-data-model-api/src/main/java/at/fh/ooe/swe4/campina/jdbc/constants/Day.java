package at.fh.ooe.swe4.campina.jdbc.constants;

import java.util.Objects;

/**
 * Enumeration which specifies the available days.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public enum Day {
	MONDAY("Montag", 0),
	THUESDAY("Dienstag", 1),
	WEDNESDAY("Mittwoch", 2),
	THURSDAY("Donnerstag", 3),
	FRIDAY("Freitag", 4),
	SATARDAY("Samstag", 5),
	SUNDAY("Sontag", 6);

	public final String	label;
	public final int	ordinal;

	private Day(String label, int ordinal) {
		this.label = label;
		this.ordinal = ordinal;
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
