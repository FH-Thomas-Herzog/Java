package at.fh.ooe.swe4.campina.service.api;

import java.io.Serializable;
import java.rmi.Remote;

import at.fh.ooe.swe4.campina.jpa.LoginEvent;
import at.fh.ooe.swe4.campina.service.api.spec.Dao;

public interface LoginEventService extends Remote, Dao<Integer, LoginEvent> {

}
