package at.fh.ooe.swe4.campina.persistence.impl;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import at.fh.ooe.swe4.campina.persistence.api.AbstractEntity;
import at.fh.ooe.swe4.campina.persistence.api.EntityManager;

/**
 * This is a light weight entity manager which allows to perform JPA like
 * operations on the backed entity.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 18, 2015
 * @param <E>
 */
public class EntityManagerImpl<E extends AbstractEntity> implements EntityManager<E> {

	private final Class<E>																clazz;
	private final Table																	table;
	private List<ColumnMetadata>														columnMetataDataList;

	private static final Map<Class<? extends AbstractEntity>, Map<Statement, String>>	cache					= new ConcurrentHashMap<Class<? extends AbstractEntity>, Map<Statement, String>>();
	private static final String															INSERT_TEMPLATE			= "INSERT INTO %s (%s, version) VALUES (%s, 1);";
	private static final String															UPDATE_TEMPLATE			= "UPDATE %s SET %s, version=version+1 WHERE id=?;";
	private static final String															DELETE_TEMPLATE			= "DELETE FROM %s WHERE id=?;";
	private static final String															SELECT_BY_ID_TEMPLATE	= "SELECT id, %s, version FROM %s WHERE id=?";
	private static final String															SELECT_ID_BY_ID_VERSION	= "SELECT id FROM %s WHERE id=? AND version=?";
	private static final String															SELECT_BY_TYPE			= "SELECT id, %s, version FROM %s";
	private static final String															SELECT_LAST_INSERT_ID	= "SELECT LAST_INSERT_ID();";
	private static final Logger															log						= Logger.getLogger(EntityManagerImpl.class);
	private static final List<String>													EXCLUDE_FIELDS			= Arrays.asList(new String[] {
																																				"id",
																																				"version"
																												});

	/**
	 * This enumeration specifies the supported statements.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 19, 2015
	 */
	private static enum Statement {
		INSERT,
		UPDATE,
		DELETE,
		SELECT_BY_ID,
		SELECT_ID_BY_ID_VERSION,
		SELECT_BY_TYPE,
		SELECT_LAST_INSERT_ID;
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

	@Override
	public E saveOrUpdate(final Connection con, final E entity) throws SQLException {
		Objects.requireNonNull(con);
		Objects.requireNonNull(entity);

		final Statement stmt = (entity.getId() == null) ? Statement.INSERT : Statement.UPDATE;
		String sql = cache.get(clazz)
							.get(stmt);
		try (final PreparedStatement pstmt = con.prepareStatement(sql);) {
			final List<Object> values = getValues(entity);

			for (int i = 0; i < columnMetataDataList.size(); i++) {
				final ColumnMetadata col = columnMetataDataList.get(i);
				final Object value = values.get(i);
				final int sqlType = converttoSqlType(col.typeClass);

				if (value == null) {
					pstmt.setNull(i + 1, sqlType);
				} else {
					pstmt.setObject(i + 1, convertValueFromEntity(value), sqlType);
				}
			}

			if (entity.getId() != null) {
				pstmt.setInt(columnMetataDataList.size() + 1, entity.getId());
			}

			pstmt.executeUpdate();

			// set id
			if (entity.getId() == null) {
				sql = SELECT_LAST_INSERT_ID;
				final PreparedStatement lastInsertIdPstmt = con.prepareStatement(sql);
				final ResultSet resultId = lastInsertIdPstmt.executeQuery();
				resultId.next();
				entity.setId(resultId.getInt(1));
				entity.setVersion(Long.valueOf(1));
			} else {
				entity.setVersion(entity.getVersion() + 1);
			}
		} catch (Throwable e) {
			log.error("Error on saveOrUpdate");
			log.error(sql);
			throw e;
		}
		return entity;
	}

	@Override
	public void delete(final Connection con, final E entity) throws SQLException {
		Objects.requireNonNull(con);
		Objects.requireNonNull(entity);
		Objects.requireNonNull(entity.getId(), "Cannot delete entity with null id");

		// check for existing entity with set id
		checkForExistingWithVersion(con, entity);

		// delete entity
		final String sql = cache.get(clazz)
								.get(Statement.DELETE);
		try (
				final PreparedStatement pstmt = con.prepareStatement(sql);) {
			System.out.println(cache.get(clazz)
									.get(Statement.DELETE));
			pstmt.setInt(1, entity.getId());
			pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error("Error on delete");
			log.error(sql);
			throw e;
		}
	}

	@Override
	public E byId(final Connection con, Integer id) throws SQLException {
		Objects.requireNonNull(con);
		Objects.requireNonNull(id);

		// full by id
		final E entity = newEntity();
		final String sql = cache.get(clazz)
								.get(Statement.SELECT_BY_ID);
		try (
				final PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, id);
			final ResultSet result = pstmt.executeQuery();
			if (result.next()) {
				entity.setId(result.getInt(1));
				fillEntity(result, entity, 1);
				entity.setVersion(result.getLong(columnMetataDataList.size() + 2));
			} else {
				throw new SQLException("Entity not found for id");
			}
		} catch (Throwable e) {
			log.error("Error on byId");
			log.error(sql);
			throw e;
		}
		return entity;
	}

	@Override
	public List<E> byType(final Connection con) throws SQLException {
		Objects.requireNonNull(con);

		final List<E> entities = new ArrayList<>();
		try (
				final PreparedStatement pstmt = con.prepareStatement(cache.get(clazz)
																			.get(Statement.SELECT_BY_TYPE));) {
			final ResultSet result = pstmt.executeQuery();

			while (result.next()) {
				final E entity = newEntity();
				entity.setId(result.getInt(1));
				fillEntity(result, entity, 1);
				entity.setVersion(result.getLong(columnMetataDataList.size() + 2));
				entities.add(entity);
			}
		}
		return entities;
	}

	@Override
	public List<E> byQuery(final Connection con, final String query, Object... args) throws SQLException {
		Objects.requireNonNull(query);
		Objects.requireNonNull(args);

		final List<E> entities = new ArrayList<>();
		try (final PreparedStatement pstmt = con.prepareStatement(query)) {
			for (int i = 0; i < args.length; i++) {
				pstmt.setObject(i + 1, args[i]);
			}
			final ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				final E entity = newEntity();
				entity.setId(result.getInt(1));
				fillEntity(result, entity, 1);
				entity.setVersion(result.getLong(columnMetataDataList.size() + 2));
				entities.add(entity);
			}
		} catch (SQLException e) {
			log.error("Error on bybyQuery");
			log.error(query);
			throw new SQLException("Could not execute custom query", e);
		}

		return entities;
	}

	@Override
	public List<ColumnMetadata> getColumnMeta() {
		return Collections.unmodifiableList(columnMetataDataList);
	}

	@Override
	public String getTableName() {
		return table.name();
	}

	@Override
	public String getColumnNames(String prefix) {
		final List<String> names = new ArrayList<>(columnMetataDataList.size());
		for (ColumnMetadata colMeta : columnMetataDataList) {
			final String name;
			if (prefix != null) {
				name = prefix + "." + colMeta.column.name()
													.toLowerCase();
			} else {
				name = colMeta.column.name()
										.toLowerCase();
			}
			names.add(name);
		}
		return StringUtils.join(names, ",");
	}

	/**
	 * Files the given entity instance with the in the {@link ResultSet}
	 * contained values. The result set ust hold all columns including the id.
	 * 
	 * @param result
	 * @param entity
	 * @throws SQLException
	 */
	@Override
	public void fillEntity(ResultSet result, AbstractEntity entity, int offset) throws SQLException {
		Objects.requireNonNull(result);
		Objects.requireNonNull(entity);

		ColumnMetadata colMeta = null;
		Object value = null;
		try {
			for (int i = 0; i < columnMetataDataList.size(); i++) {
				colMeta = columnMetataDataList.get(i);
				value = result.getObject(offset + i + 1);
				final Object convertedValue;
				if (value != null) {
					convertedValue = convertValueToEntity(colMeta, value);
				} else {
					convertedValue = value;
				}
				clazz.getMethod(colMeta.setter, colMeta.typeClass)
						.invoke(entity, convertedValue);
			}
		} catch (Throwable e) {
			if (value != null) {

			}
			throw new java.lang.IllegalStateException("Could not fill entity: " + ((colMeta != null) ? colMeta.setter : ""), e);
		}
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
		this.columnMetataDataList = getColumnMetataDataList();
		Map<Statement, String> statementCache = cache.putIfAbsent(clazz, newCache);

		// only if no other instance has initialized cache for this class
		if (statementCache == null) {
			final List<String> columnNames = new ArrayList<>(columnMetataDataList.size());
			final List<String> parameters = new ArrayList<>(columnMetataDataList.size());
			final List<String> updateParameters = new ArrayList<>(columnMetataDataList.size());
			for (ColumnMetadata col : columnMetataDataList) {
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
			cache.get(clazz)
					.put(Statement.SELECT_BY_ID, String.format(SELECT_BY_ID_TEMPLATE, cols, tableName));
			cache.get(clazz)
					.put(Statement.SELECT_ID_BY_ID_VERSION, String.format(SELECT_ID_BY_ID_VERSION, tableName));
			cache.get(clazz)
					.put(Statement.SELECT_BY_TYPE, String.format(SELECT_BY_TYPE, cols, tableName));

			log.info(cache.get(clazz)
							.get(Statement.INSERT));
			log.info(cache.get(clazz)
							.get(Statement.UPDATE));
			log.info(cache.get(clazz)
							.get(Statement.SELECT_BY_ID));
			log.info(cache.get(clazz)
							.get(Statement.SELECT_ID_BY_ID_VERSION));
			log.info(cache.get(clazz)
							.get(Statement.SELECT_BY_TYPE));
			log.info(SELECT_LAST_INSERT_ID);
		}
		log.info("--------------------------------------------");
		log.info("Finished Setup for " + this.getClass()
												.getSimpleName() + "<" + clazz.getSimpleName() + ">");
		log.info("--------------------------------------------");
	}

	// ####################################################################
	// Private section
	// ####################################################################

	/**
	 * Checks if the given entity exists on the database with its set id and
	 * version.
	 * 
	 * @param con
	 *            the underlying connection
	 * @param entity
	 *            the entity to check
	 * @throws SQLException
	 *             if the entity is not found with its id and version on the
	 *             database
	 * @throws NullPointerException
	 *             <ul>
	 *             <li>con is null</li>
	 *             <li>entity is null</li>
	 *             <li>entity id is null</li>
	 *             </ul>
	 */
	private void checkForExistingWithVersion(final Connection con, final E entity) throws SQLException {
		Objects.requireNonNull(con);
		Objects.requireNonNull(entity);
		Objects.requireNonNull(entity.getId());

		// entity does exist with its set version
		final PreparedStatement existPstmt = con.prepareStatement(cache.get(clazz)
																		.get(Statement.SELECT_ID_BY_ID_VERSION));
		existPstmt.setInt(1, entity.getId());
		existPstmt.setLong(2, entity.getVersion());
		if (!existPstmt.executeQuery()
						.next()) {
			throw new SQLException("Entity not found for set id and version !!! id: " + entity.getId()
																								.toString() + " | version: " + entity.getVersion()
																																		.toString());
		}
	}

	/**
	 * Gets the {@link Column} annotations of the backed entity class.
	 * 
	 * @return the list of column annotations
	 */
	private List<ColumnMetadata> getColumnMetataDataList() {
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

		for (ColumnMetadata colMeta : columnMetataDataList) {
			try {
				values.add(clazz.getMethod(colMeta.getter)
								.invoke(entity));
			} catch (Throwable e) {
				throw new IllegalArgumentException("Could not invoke '" + colMeta.getter + "'", e);
			}
		}
		return values;
	}

	/**
	 * Converts the sql result returned value to the proper entity type.
	 * 
	 * @param colMeta
	 *            the current column
	 * @param value
	 *            the current column value
	 * @return the converted value
	 * @throws IllegalStateException
	 *             if the many to one relation entity could not be instantiated
	 */
	private Object convertValueToEntity(ColumnMetadata colMeta, Object value) {
		if (value == null) {
			return value;
		} else if (Calendar.class.isAssignableFrom(colMeta.typeClass)) {
			final Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(((Timestamp) value).getTime());
			return cal;
		} else if (BigDecimal.class.isAssignableFrom(colMeta.typeClass)) {
			return ((BigDecimal) value);
		} else if (Enum.class.isAssignableFrom(colMeta.typeClass)) {
			return Enum.valueOf((Class<Enum>) colMeta.typeClass, (String) value);
		}
		if ((AbstractEntity.class.isAssignableFrom(colMeta.typeClass))) {
			try {
				final AbstractEntity fk = (AbstractEntity) colMeta.typeClass.newInstance();
				fk.setId((Integer) value);
				return fk;
			} catch (Exception e) {
				throw new IllegalStateException("Could not create many-to-one relation entity '" + colMeta.typeClass.getName() + "'");
			}

		} else if (Boolean.class.isAssignableFrom(colMeta.typeClass)) {
			return (((Integer) value).equals(1)) ? Boolean.TRUE : Boolean.FALSE;
		}
		return value;
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
	private Object convertValueFromEntity(final Object value) {
		Objects.requireNonNull(value);

		if (value instanceof Calendar) {
			return new Timestamp(((Calendar) value).getTimeInMillis());
		} else if (value instanceof AbstractEntity) {
			return ((AbstractEntity) value).getId();
		} else if (value instanceof BigDecimal) {
			return ((BigDecimal) value).doubleValue();
		} else if (value instanceof Enum) {
			return ((Enum) value).name();
		} else {
			return value;
		}
	}

	/**
	 * Converts the entity value type to the proper sql type.
	 * 
	 * @param clazz
	 *            the return type class of the entity getter
	 * @return the integer representing the sql type
	 */
	private int converttoSqlType(final Class<?> clazz) {
		Objects.requireNonNull(clazz);

		if (String.class.isAssignableFrom(clazz)) {
			return Types.VARCHAR;
		} else if (Integer.class.isAssignableFrom(clazz)) {
			return Types.INTEGER;
		} else if (BigDecimal.class.isAssignableFrom(clazz)) {
			return Types.DOUBLE;
		} else if (Boolean.class.isAssignableFrom(clazz)) {
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

	/**
	 * Creates a new entity instance of the backed tpye
	 * 
	 * @return the new instance
	 * @throws IllegalStateException
	 *             if the instantiation fails
	 */
	private E newEntity() {
		final E entity;
		try {
			entity = clazz.newInstance();
		} catch (Throwable e) {
			throw new IllegalStateException("Could not create new isntance");
		}
		return entity;
	}
}
