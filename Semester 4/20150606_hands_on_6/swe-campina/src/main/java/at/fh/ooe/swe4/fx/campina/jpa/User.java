package at.fh.ooe.swe4.fx.campina.jpa;

import java.io.Serializable;

public class User extends AbstractEntity<Integer> {

	private static final long	serialVersionUID	= 94057306870207307L;

	private String				firstName;
	private String				lastName;

	public User() {
	}

	public User(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Integer getId() {
		return _getId();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
