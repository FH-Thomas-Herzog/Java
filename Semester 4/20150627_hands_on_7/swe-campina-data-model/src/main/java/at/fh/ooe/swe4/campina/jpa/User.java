package at.fh.ooe.swe4.campina.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import at.fh.ooe.swe4.campina.jpa.api.AbstractEntity;

/**
 * The campina user.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
@Table(name = "USER", schema = "CAMPINA")
public class User extends AbstractEntity<Integer> {

	private static final long	serialVersionUID	= 94057306870207307L;

	private String				firstName;
	private String				lastName;
	private String				email;
	private String				username;
	private String				password;
	private Boolean				adminFlag			= Boolean.FALSE;
	private Boolean				blockedFlag			= Boolean.FALSE;

	/**
	 * 
	 */
	public User() {
	}

	/**
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param email
	 */
	public User(Integer id, String firstName, String lastName, String email) {
		super(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	@Id
	@Column(name = "ID")
	public Integer getId() {
		return _getId();
	}

	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LAST_NAME")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "USERNAME")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "ADMIN_FLAG")
	public Boolean getAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(Boolean adminFlag) {
		this.adminFlag = adminFlag;
	}

	@Column(name = "BLOCKED_FLAG")
	public Boolean getBlockedFlag() {
		return blockedFlag;
	}

	public void setBlockedFlag(Boolean blockedFlag) {
		this.blockedFlag = blockedFlag;
	}
}
