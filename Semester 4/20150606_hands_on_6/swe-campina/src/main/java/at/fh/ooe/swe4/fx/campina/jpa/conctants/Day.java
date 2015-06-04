package at.fh.ooe.swe4.fx.campina.jpa.conctants;

import java.util.Objects;

public enum Day {
	UNSET(""),
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

	public String getLabel() {
		return label;
	}

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
