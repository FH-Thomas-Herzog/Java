package at.fh.ooe.swe4.campina.dao.exception;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class EmailAlreadyUsedException extends RuntimeException {

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
