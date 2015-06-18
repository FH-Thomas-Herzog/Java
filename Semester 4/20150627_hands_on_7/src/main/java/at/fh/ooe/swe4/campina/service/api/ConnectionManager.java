package at.fh.ooe.swe4.campina.service.api;

import java.sql.Connection;

/**
 * This interface specifies an conection manager which provides connections for
 * services. The implementation decides if the connections are either cached or
 * created new each time {@link ConnectionManager#getClass()} is called.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 18, 2015
 */
public interface ConnectionManager {

	/**
	 * Returns a jdbc connection.
	 * 
	 * @param batchStatements
	 *            true if multiple statements per transaction shall be supported
	 * @return the jdbc connection
	 */
	public Connection getConnection(boolean batchStatements);
}
