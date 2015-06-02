package at.fh.ooe.swe4.fx.campina.jpa;

public class User extends AbstractEntity<Integer> {

	private static final long	serialVersionUID	= 94057306870207307L;

	private String				firstName;
	private String				lastName;
	private String				email;
	private String				username;
	private String				password;

	public User() {
	}

	public User(Integer id, String firstName, String lastName, String email) {
		super(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
