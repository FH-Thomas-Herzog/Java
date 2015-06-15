package at.fh.ooe.swe4.campina.service.impl;

import at.fh.ooe.swe4.campina.jpa.LoginEvent;
import at.fh.ooe.swe4.campina.service.api.LoginEventService;

public class LoginEventServiceImpl implements LoginEventService {

	public LoginEventServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public LoginEvent save(LoginEvent entity) {
		System.out.println("hello save login event");
		return null;
	}

	@Override
	public LoginEvent delete(LoginEvent entity) {
		System.out.println("hello delete login event");
		return null;
	}
}
