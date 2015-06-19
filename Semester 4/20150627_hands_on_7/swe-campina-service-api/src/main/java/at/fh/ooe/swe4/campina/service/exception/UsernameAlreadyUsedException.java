package at.fh.ooe.swe4.campina.service.exception;

import java.rmi.RemoteException;

public class UsernameAlreadyUsedException extends RemoteException {

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
