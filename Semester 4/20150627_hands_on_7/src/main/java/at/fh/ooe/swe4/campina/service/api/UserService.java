package at.fh.ooe.swe4.campina.service.api;

import java.rmi.Remote;

import at.fh.ooe.swe4.campina.jpa.User;
import at.fh.ooe.swe4.campina.service.api.spec.Service;

public interface UserService extends Remote, Service<Integer, User> {

}
