package at.fh.ooe.swe4.campina.persistence.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * This interface specifies an entity manager which is used for entity types .
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 19, 2015
 * @param <E>
 *            the entity type the isntance is for
 */
public interface EntityManager<E extends AbstractEntity> {

	/**
	 * Saves or updates the given entity depending on set id or not.
	 * 
	 * @param con
	 *            the underlying connection
	 * @param entity
	 *            the entity to save or update
	 * @return the saved or updated entity
	 * @throws SQLException
	 *             if the save or update fails due an sql error.
	 * @throws NullPointerException
	 *             if the entity is null
	 */
	public E saveOrUpdate(Connection con, E entity) throws SQLException;

	/**
	 * Deletes the given entity.
	 * 
	 * @param con
	 *            the underlying connection
	 * @param entity
	 *            the entity to be deleted
	 * @throws SQLException
	 *             if the deletion fails
	 * @throws NullPointerException
	 *             if con or entity are null
	 */
	public void delete(Connection con, E entity) throws SQLException;

	/**
	 * Gets all entries from the backed entity type.
	 * 
	 * @param con
	 *            the underlying connection
	 * @return the found entity
	 * @throws SQLException
	 *             if the entity could not be found
	 */
	public List<E> byType(Connection con) throws SQLException;

	/**
	 * Gets the entity by its id.
	 * 
	 * @param con
	 *            the underlying connection
	 * @param id
	 *            TODO
	 * @return the given instance with filled result
	 * @throws SQLException
	 *             if the fetch fails
	 * @throws NullPointerException
	 *             if con or entity are null
	 */
	public E byId(Connection con, Integer id) throws SQLException;

	/**
	 * Gets the table name with schema (if set) of the backed entity type.
	 * 
	 * @return the table name
	 */
	public String getTableName();

}