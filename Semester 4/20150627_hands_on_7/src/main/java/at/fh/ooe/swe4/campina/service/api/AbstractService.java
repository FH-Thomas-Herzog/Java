package at.fh.ooe.swe4.campina.service.api;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractService implements Serializable {

	private static final long	serialVersionUID	= 5368818320986749236L;

	public AbstractService() {
	}

	protected Connection getConnection() {
		// try {
		// } catch (SQLException | ClassNotFoundException e) {
		// // TODO: handle exception
		// }
		return null;
	}
}
