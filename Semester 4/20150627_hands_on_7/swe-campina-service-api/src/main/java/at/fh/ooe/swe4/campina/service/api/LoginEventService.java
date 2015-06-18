package at.fh.ooe.swe4.campina.service.api;

import java.rmi.Remote;

import at.fh.ooe.swe4.campina.jdbc.LoginEvent;
import at.fh.ooe.swe4.campina.rmi.api.service.RemoteDaoService;

public interface LoginEventService extends Remote, RemoteDaoService<Integer, LoginEvent> {

}
