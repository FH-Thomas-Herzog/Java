package at.fh.ooe.swe4.campina.dao.exception;

/**
 * This exception indicates that an username is already used by another user.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 21, 2015
 */
public class UsernameAlreadyUsedException extends RuntimeException {

	private static final long	serialVersionUID	= 2301658696430408670L;

	public UsernameAlreadyUsedException() {
		// TODO Auto-generated constructor stub
	}

	public UsernameAlreadyUsedException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	public UsernameAlreadyUsedException(String s, Throwable cause) {
		super(s, cause);
		// TODO Auto-generated constructor stub
	}

}
