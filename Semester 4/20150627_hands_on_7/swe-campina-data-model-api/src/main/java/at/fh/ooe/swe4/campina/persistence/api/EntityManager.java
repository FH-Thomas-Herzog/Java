package at.fh.ooe.swe4.campina.persistence.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;

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
	 * This is a helper class which hold the metadata from the @Column annotated
	 * methods which represent the dataabse columns
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 19, 2015
	 */
	public static final class ColumnMetadata {

		public final Column		column;
		public final String		getter;
		public final String		setter;
		public final Class<?>	typeClass;

		/**
		 * @param column
		 * @param methodName
		 * @param typeClass
		 */
		public ColumnMetadata(Column column, String methodName, Class<?> typeClass) {
			super();
			Objects.requireNonNull(column);
			Objects.requireNonNull(methodName);
			Objects.requireNonNull(typeClass);

			this.column = column;
			this.getter = methodName;
			this.setter = methodName.replace("get", "set");
			this.typeClass = typeClass;
		}

	}

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
	 * Gets the entities by custom query.
	 * 
	 * @param con
	 *            the underlying connection
	 * @param query
	 *            the query to execute
	 * @param args
	 *            the arguments for the query
	 * @return the entity list
	 * @throws SQLException
	 *             if the query execution fails
	 */
	public List<E> byQuery(Connection con, String query, Object... args) throws SQLException;

	/**
	 * Fills the entity with the values of the result set. It is assumed that
	 * the columns in the same order as this entity managers relys on and that
	 * all of the columns are present.
	 * 
	 * @param result
	 *            the result set
	 * @param entity
	 *            the entity to fill
	 * @param offset
	 *            the offset of the column index
	 * @throws SQLException
	 *             if the filling fails
	 */
	public void fillEntity(ResultSet result, AbstractEntity entity, int offset) throws SQLException;

	/**
	 * Gets the table name with schema (if set) of the backed entity type.
	 * 
	 * @return the table name
	 */
	public String getTableName();

	/**
	 * Gets the column names of the backed entity in the form of name-1, name-2,
	 * ... , name-n
	 * 
	 * @param prefix
	 *            TODO
	 * 
	 * @return the backed entity column names
	 */
	public String getColumnNames(String prefix);

	/**
	 * Returns an unmodifiable list containing the column metadata.
	 * 
	 * @return the column metadata
	 */
	public List<ColumnMetadata> getColumnMeta();
}