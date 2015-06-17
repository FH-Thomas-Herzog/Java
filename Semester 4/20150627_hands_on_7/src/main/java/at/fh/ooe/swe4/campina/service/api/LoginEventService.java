package at.fh.ooe.swe4.campina.service.api;

import java.rmi.Remote;

import at.fh.ooe.swe4.campina.jpa.LoginEvent;
import at.fh.ooe.swe4.campina.service.api.spec.Service;

public interface LoginEventService extends Remote, Service<Integer, LoginEvent> {

}
