package at.fh.ooe.swe4.campina.service.api;

import at.fh.ooe.swe4.campina.jdbc.User;
import at.fh.ooe.swe4.campina.rmi.api.service.RemoteDaoService;

public interface UserDao extends RemoteDaoService<Integer, User> {

}
