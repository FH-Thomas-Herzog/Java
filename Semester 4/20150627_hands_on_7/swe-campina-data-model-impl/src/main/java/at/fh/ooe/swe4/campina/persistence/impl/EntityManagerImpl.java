package at.fh.ooe.swe4.campina.persistence.impl;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Column;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import at.fh.ooe.swe4.campina.jdbc.Menu;
import at.fh.ooe.swe4.campina.jdbc.MenuEntry;
import at.fh.ooe.swe4.campina.jdbc.Order;
import at.fh.ooe.swe4.campina.jdbc.User;
import at.fh.ooe.swe4.campina.jdbc.api.AbstractEntity;
import at.fh.ooe.swe4.campina.jdbc.api.ConnectionManager;
import at.fh.ooe.swe4.campina.persistence.impl.ConnectionManagerImpl.DbMetadata;

/**
 * This is a light weight entity manager which allows to perform JPA like
 * operations on the entity.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 18, 2015
 * @param <E>
 */
public class EntityManagerImpl<E extends AbstractEntity> {

	private final Class<E>																clazz;
	private final Table																	table;
	private List<ColumnMetadata>														columns;

	private static final Map<Class<? extends AbstractEntity>, Map<Statement, String>>	cache			= new ConcurrentHashMap<Class<? extends AbstractEntity>, Map<Statement, String>>();
	private static final String															INSERT_TEMPLATE	= "INSERT INTO %s (%s, version) VALUES (%s, 1);";
	private static final String															UPDATE_TEMPLATE	= "UPDATE %s SET %s, version=version+1 WHERE id=:id AND version=:version;";
	private static final String															DELETE_TEMPLATE	= "DELETE FROM %s WHERE id=? AND version=?;";
	private static final Logger															log				= Logger.getLogger(EntityManagerImpl.class);
	private static final List<String>													EXCLUDE_FIELDS	= Arrays.asList(new String[] {
																																		"id",
																																		"version"
																										});

	/**
	 * This is a helper class which hold the metadata from the @Column annotated
	 * methods which represent the dataabse columns
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 19, 2015
	 */
	private static final class ColumnMetadata {

		public final Column		column;
		public final String		methodName;
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
			this.methodName = methodName;
			this.typeClass = typeClass;
		}

	}

	/**
	 * This enumeration specifies the supported statements.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 19, 2015
	 */
	private static enum Statement {
		INSERT,
		UPDATE,
		DELETE;
	}

	/**
	 * @param clazz
	 *            the backed entity class
	 * @throws NullPointerException
	 *             <ul>
	 *             <li>clazz is null</li>
	 *             <li>clazz has no @Table annotation</li>
	 *             </ul>
	 */
	public EntityManagerImpl(final Class<E> clazz) {
		Objects.requireNonNull(clazz);
		Objects.requireNonNull(clazz.getAnnotation(Table.class), "Given entity class has no Table annotation");

		this.clazz = clazz;
		this.table = clazz.getAnnotation(Table.class);

		setup();
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
	public E saveOrUpdate(final Connection con, final E entity) throws SQLException {
		Objects.requireNonNull(con);
		Objects.requireNonNull(entity);

		final Statement stmt = (entity.getId() == null) ? Statement.INSERT : Statement.UPDATE;
		final PreparedStatement pstmt = con.prepareStatement(cache.get(clazz)
																	.get(stmt));

		final List<Object> values = getValues(entity);

		for (int i = 0; i < columns.size(); i++) {
			final ColumnMetadata col = columns.get(i);
			final Object value = values.get(i);
			final int sqlType = converttoSqlType(col.typeClass);

			if (value == null) {
				pstmt.setNull(i + 1, sqlType);
			} else {
				pstmt.setObject(i + 1, convertValue(value), sqlType);
			}
		}

		pstmt.executeUpdate();

		// TODO: set id from sequence
		entity.setVersion(Long.valueOf(1));
		return entity;
	}

	public void delete(final Connection con, final E entity) throws SQLException {
		Objects.requireNonNull(con);
		Objects.requireNonNull(entity);
		Objects.requireNonNull(entity.getId(), "Cannot delete entity with null id");

		// TODO: Need to check if entity exists (version check)
		final PreparedStatement pstmt = con.prepareStatement(cache.get(clazz)
																	.get(Statement.DELETE));
		System.out.println(cache.get(clazz)
								.get(Statement.DELETE));
		pstmt.setInt(1, entity.getId());
		pstmt.setLong(2, entity.getVersion());

		pstmt.executeUpdate();
	}

	/**
	 * Setup this service util by creating all supported statements for the
	 * backed entity class if and only if no other instance which backs this
	 * entity type has already created the statements.
	 */
	public void setup() {
		log.info("--------------------------------------------");
		log.info("Started Setup for " + this.getClass()
											.getSimpleName() + "<" + clazz.getSimpleName() + ">");
		log.info("--------------------------------------------");

		Map<Statement, String> newCache = new HashMap<Statement, String>();
		this.columns = getColumns();
		Map<Statement, String> statementCache = cache.putIfAbsent(clazz, newCache);

		// only if no other instance has initialized cache for this class
		if (statementCache == null) {
			final List<String> columnNames = new ArrayList<>(columns.size());
			final List<String> parameters = new ArrayList<>(columns.size());
			final List<String> updateParameters = new ArrayList<>(columns.size());
			for (ColumnMetadata col : columns) {
				final String name = col.column.name()
												.toLowerCase();
				columnNames.add(name);
				parameters.add("?");
				updateParameters.add(name + "=?");
			}

			// -- prepare statements --
			final String cols = StringUtils.join(columnNames, ", ");
			final String params = StringUtils.join(parameters, ", ");
			final String updateParams = StringUtils.join(updateParameters, ", ");
			final String tableName = (table.schema().isEmpty()) ? table.name()
																		.toLowerCase() : (new StringBuilder(table.schema()).append(".")
																															.append(table.name())
																															.toString().toLowerCase());

			log.info("Creating " + StringUtils.join(Statement.values(), ", ") + " Statements");
			cache.get(clazz)
					.put(Statement.INSERT, String.format(INSERT_TEMPLATE, tableName, cols, params));
			cache.get(clazz)
					.put(Statement.UPDATE, String.format(UPDATE_TEMPLATE, tableName, updateParams));
			cache.get(clazz)
					.put(Statement.DELETE, String.format(DELETE_TEMPLATE, tableName));

			log.info(cache.get(clazz)
							.get(Statement.INSERT));
			log.info(cache.get(clazz)
							.get(Statement.UPDATE));
			log.info(cache.get(clazz)
							.get(Statement.DELETE));
		}
		log.info("--------------------------------------------");
		log.info("Finished Setup for " + this.getClass()
												.getSimpleName() + "<" + clazz.getSimpleName() + ">");
		log.info("--------------------------------------------");
	}

	/**
	 * Gets the {@link Column} annotations of the backed entity class.
	 * 
	 * @return the list of column annotations
	 */
	private List<ColumnMetadata> getColumns() {
		try {
			final List<ColumnMetadata> columns = new LinkedList<>();

			final Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				final Column col = method.getAnnotation(Column.class);
				if ((col != null) && (!EXCLUDE_FIELDS.contains(col.name()
																	.toLowerCase()))) {
					columns.add(new ColumnMetadata(col, method.getName(), method.getReturnType()));
				}
			}
			return Collections.unmodifiableList(columns);
		} catch (Throwable e) {
			throw new IllegalArgumentException("Could not get column information", e);
		}
	}

	/**
	 * Gets a list of values from the given entity
	 * 
	 * @param entity
	 *            the entity to retrieve values from
	 * @return the list of the retrieved values
	 * @throws IllegalStateException
	 *             if the value could not be retrieved by reflection
	 * @throws NullPointerException
	 *             if the entity is null
	 */
	private List<Object> getValues(final E entity) throws IllegalStateException {
		Objects.requireNonNull(entity);

		final List<Object> values = new LinkedList<>();

		for (ColumnMetadata colMeta : columns) {
			try {
				values.add(clazz.getMethod(colMeta.methodName)
								.invoke(entity));
			} catch (Throwable e) {
				throw new IllegalArgumentException("Could not invoke '" + colMeta.methodName + "'", e);
			}
		}
		return values;
	}

	/**
	 * Converts the given value to the proper sql type.
	 * 
	 * @param value
	 *            the value ot be converted
	 * @return the converted value
	 * @throws IllegalStateException
	 *             if the value type cannot be converted
	 */
	private Object convertValue(final Object value) {
		Objects.requireNonNull(value);

		if (value instanceof Calendar) {
			return new Timestamp(((Calendar) value).getTimeInMillis());
		} else if (value instanceof AbstractEntity) {
			return ((AbstractEntity) value).getId();
		} else if (value instanceof BigDecimal) {
			return ((BigDecimal) value).doubleValue();
		} else {
			return value;
		}
	}

	/**
	 * Converts the entity value type to the proper sql type.
	 * 
	 * @param clazz
	 *            the return type class of the entity getter
	 * @return the int representing the sql type
	 */
	private int converttoSqlType(final Class<?> clazz) {
		Objects.requireNonNull(clazz);

		if (clazz.equals(String.class)) {
			return Types.VARCHAR;
		} else if (clazz.equals(Integer.class)) {
			return Types.INTEGER;
		} else if (clazz.equals(BigInteger.class)) {
			return Types.DOUBLE;
		} else if (clazz.equals(Boolean.class)) {
			return Types.SMALLINT;
		} else if (AbstractEntity.class.isAssignableFrom(clazz)) {
			return Types.INTEGER;
		} else if (Enum.class.isAssignableFrom(clazz)) {
			return Types.VARCHAR;
		} else if (Calendar.class.isAssignableFrom(clazz)) {
			return Types.TIMESTAMP;
		}
		return 0;
	}

	public static void main(String args[]) {
		try {
			final EntityManagerImpl<User> em = new EntityManagerImpl<>(User.class);
			new EntityManagerImpl<>(Menu.class);
			new EntityManagerImpl<>(MenuEntry.class);
			new EntityManagerImpl<>(Order.class);

			final User user = new User();
			user.setFirstName("Thomas");
			user.setLastName("Herzog");
			user.setUsername("cchet");
			user.setEmail("t.t@t.at");
			user.setPassword("xxxxxxx");
			user.setAdminFlag(Boolean.TRUE);
			user.setBlockedFlag(Boolean.FALSE);

			final DbMetadata metadata = new DbMetadata(DbConfigParam.DRIVER.val(),
					DbConfigParam.URL.val(),
					DbConfigParam.USER.val(),
					DbConfigParam.PASSWORD.val(),
					Integer.valueOf(DbConfigParam.ISOLATION.val()));
			final ConnectionManager cm = new ConnectionManagerImpl(metadata);
			final Connection con = cm.getConnection(Boolean.TRUE);
			// em.saveOrUpdate(con, user);
			// con.commit();
			user.setId(2);
			user.setVersion(Long.valueOf(1));
			em.delete(con, user);
			con.commit();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
