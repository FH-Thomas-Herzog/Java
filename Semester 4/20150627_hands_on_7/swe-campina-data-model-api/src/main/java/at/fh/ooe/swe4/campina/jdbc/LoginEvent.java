package at.fh.ooe.swe4.campina.jdbc;

import at.fh.ooe.swe4.campina.jdbc.api.AbstractEntity;

/**
 * The login event which represents an valid login maybe an invalid too.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class LoginEvent extends AbstractEntity<Integer> {

	private static final long	serialVersionUID	= 1782498743658504195L;

	// TODO: Add user references and calendar fields for login date

	/**
	 * 
	 */
	public LoginEvent() {
		this(null);
	}

	/**
	 * @param id
	 */
	public LoginEvent(Integer id) {
		super(id);
	}
}
