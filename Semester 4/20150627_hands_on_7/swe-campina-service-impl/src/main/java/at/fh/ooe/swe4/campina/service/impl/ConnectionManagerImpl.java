package at.fh.ooe.swe4.campina.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

import org.apache.log4j.Logger;

import at.fh.ooe.swe4.campina.service.api.ConnectionManager;

/**
 * This is the default implementation for the {@link ConnectionManager}
 * interface. Each time the method
 * {@link ConnectionManager#getConnection(boolean)} is called a new connection
 * will be created with the provided metadata.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 18, 2015
 */
public class ConnectionManagerImpl implements ConnectionManager {

	private final DbMetadata	metadata;

	private static final Logger	log	= Logger.getLogger(ConnectionManagerImpl.class);

	/**
	 * This class holds the metadata information for the used database.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 18, 2015
	 */
	public static final class DbMetadata {
		public final String		driverName;
		public final String		url;
		public final String		username;
		public final String		password;
		public final int		isolationLevel;
		public final boolean	useCredentials;

		/**
		 * For user connections
		 * 
		 * @param driverName
		 *            the driver class name
		 * @param url
		 *            the db location
		 * @param username
		 *            the username
		 * @param password
		 *            the users password
		 * @param isolationLevel
		 *            the isolation level
		 * @see Connection for the isolation levels
		 */
		public DbMetadata(String driverName, String url, String username, String password, int isolationLevel) {
			super();
			Objects.requireNonNull(driverName);
			Objects.requireNonNull(url);
			Objects.requireNonNull(username);
			Objects.requireNonNull(password);

			this.driverName = driverName;
			this.url = url;
			this.username = username;
			this.password = password;
			this.isolationLevel = isolationLevel;
			this.useCredentials = Boolean.TRUE;
		}

		/**
		 * For anonymous connections
		 * 
		 * @param driverName
		 *            the dirver class name
		 * @param url
		 *            the db location
		 */
		public DbMetadata(String driverName, String url, int isolationLevel) {
			super();
			this.driverName = driverName;
			this.url = url;
			this.username = null;
			this.password = null;
			this.isolationLevel = isolationLevel;
			this.useCredentials = Boolean.FALSE;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder(100);
			sb.append(String.format("%1$10s", "driver: "))
				.append(driverName)
				.append(System.lineSeparator())
				.append(String.format("%1$10s", "url: "))
				.append(url)
				.append(System.lineSeparator())
				.append(String.format("%1$10s", "ISO.: "))
				.append(isolationLevel)
				.append(System.lineSeparator());
			if (useCredentials) {
				sb.append(String.format("%1$10s", "username: "))
					.append(username)
					.append(System.lineSeparator())
					.append(String.format("%1$10s", "password: "))
					.append(password)
					.append(System.lineSeparator());
			}
			return sb.toString();
		}
	}

	/**
	 * @param metadata
	 *            the database metadata for creating the connections
	 */
	public ConnectionManagerImpl(final DbMetadata metadata) {
		Objects.requireNonNull(metadata);

		setup(metadata);

		this.metadata = metadata;
	}

	@Override
	public Connection getConnection(boolean batchStatements) {
		try {
			final Connection con;
			if (metadata.useCredentials) {
				con = DriverManager.getConnection(metadata.url, metadata.username, metadata.password);
			} else {
				con = DriverManager.getConnection(metadata.url);
			}
			con.setTransactionIsolation(metadata.isolationLevel);
			con.setAutoCommit(!batchStatements);
			return con;
		} catch (Exception e) {
			throw new IllegalStateException("Should not happen :(");
		}
	}

	// #######################################################################
	// Private Section
	// #######################################################################
	private void setup(final DbMetadata metadata) throws IllegalArgumentException {
		final String ln = System.lineSeparator();
		log.info("----------------------------------------------");
		log.info("Beginn Setup Connection manager");
		log.info("----------------------------------------------");
		log.info("Provided DbMetadata:"
				+ ln
				+ ln
				+ metadata.toString()
			);

		try {
			Class.forName(metadata.driverName);
		} catch (ClassNotFoundException e) {
			log.error("driver class not found", e);
			throw new IllegalArgumentException("Driver class '" + metadata.driverName + "' not found");
		}

		try {
			log.info("Trying to connect to database: " + metadata.url);
			final Connection con;
			if (metadata.useCredentials) {
				con = DriverManager.getConnection(metadata.url, metadata.username, metadata.password);
			} else {
				con = DriverManager.getConnection(metadata.url);
			}
			log.info("Trying to set isolation level: " + metadata.isolationLevel);
			con.setTransactionIsolation(metadata.isolationLevel);
			log.info("Trying to close connection");
			con.close();
		} catch (Exception e) {
			log.error("Setup failed for: ", e);
			throw new IllegalArgumentException("Exception occured during initial setup", e);
		}

		log.info("----------------------------------------------");
		log.info("End Setup Connection manager");
		log.info("----------------------------------------------");
	}

	public static void main(String args[]) {
		final DbMetadata m = new DbMetadata("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3307/test", 4);
		final ConnectionManager cm = new ConnectionManagerImpl(m);
	}
}
