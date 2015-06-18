package at.fh.ooe.swe4.campina.service.impl.constants;

import java.util.ResourceBundle;

/**
 * This enum specifies the db-config.properties used parameters
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 18, 2015
 */
public enum DbConfigParam {
	DRIVER,
	URL,
	USER,
	PASSWORD,
	ISOLATION;

	/**
	 * Gets the provided configuration
	 */
	private static final ResourceBundle	dbConfig	= ResourceBundle.getBundle("db-config");

	/**
	 * Returns the String representation of the parameter value
	 * 
	 * @return the parameter value
	 */
	public String val() {
		return dbConfig.getString(this.name());
	}
}
