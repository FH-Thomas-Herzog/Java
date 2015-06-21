package at.fh.ooe.swe4.campina.dao.exception;


/**
 * This exception indicates than an email address is already used by another
 * user.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 21, 2015
 */
public class EmailAlreadyUsedException extends RuntimeException {

	private static final long	serialVersionUID	= -7433764767593804037L;

	public EmailAlreadyUsedException() {
		// TODO Auto-generated constructor stub
	}

	public EmailAlreadyUsedException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	public EmailAlreadyUsedException(String s, Throwable cause) {
		super(s, cause);
		// TODO Auto-generated constructor stub
	}

}
