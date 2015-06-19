package at.fh.ooe.swe4.campina.service.exception;

import java.rmi.RemoteException;

public class EmailAlreadyUsedException extends RemoteException {

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
