package at.fh.ooe.swe4.campina.dao.exception;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class UsernameAlreadyUsedException extends RuntimeException {

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
