package at.fh.ooe.swe4.campina.service.api;

import java.rmi.Remote;

import at.fh.ooe.swe4.campina.jdbc.User;
import at.fh.ooe.swe4.campina.rmi.api.service.RemoteDaoService;

public interface UserService extends Remote, RemoteDaoService<Integer, User> {

}
