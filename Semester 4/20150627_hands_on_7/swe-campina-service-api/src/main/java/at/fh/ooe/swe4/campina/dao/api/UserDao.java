package at.fh.ooe.swe4.campina.dao.api;

import at.fh.ooe.swe4.campina.persistence.api.entity.User;
import at.fh.ooe.swe4.campina.rmi.api.service.RemoteDao;

/**
 * The DAO for the {@link User} entity type.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 21, 2015
 */
public interface UserDao extends RemoteDao<User> {
}
