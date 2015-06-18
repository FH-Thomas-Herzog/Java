package at.fh.ooe.swe4.campina.persistence.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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

import at.fh.ooe.swe4.campina.jdbc.User;
import at.fh.ooe.swe4.campina.jdbc.api.AbstractEntity;

/**
 * This utility class is used for handling the base operations on an entity.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 18, 2015
 * @param <E>
 */
public class ServiceDaoUtil<E extends AbstractEntity> {

	private final Class<E>																clazz;
	private final Table																	table;
	private List<Column>																columns;

	private static final Map<Class<? extends AbstractEntity>, Map<Statement, String>>	cache			= new ConcurrentHashMap<Class<? extends AbstractEntity>, Map<Statement, String>>();
	private static final String															INSERT_TEMPLATE	= "INSERT INTO %s (%s, version) VALUES (%s, 1);";
	private static final String															UPDATE_TEMPLATE	= "UPDATE %s SET %s, version=version+1 WHERE id=:id AND version=:version;";
	private static final String															DELETE_TEMPLATE	= "DELETE FROM %s WHERE id=:id AND version=:version;";
	private static final Logger															log				= Logger.getLogger(ServiceDaoUtil.class);
	private static final List<String>													EXCLUDE_FIELDS	= Arrays.asList(new String[] {
																																		"id",
																																		"version"
																										});

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
	public ServiceDaoUtil(final Class<E> clazz) {
		Objects.requireNonNull(clazz);
		Objects.requireNonNull(clazz.getAnnotation(Table.class), "Given entity class has no Table annotation");

		this.clazz = clazz;
		this.table = clazz.getAnnotation(Table.class);

		setup();
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
			for (Column col : columns) {
				final String name = col.name()
										.toLowerCase();
				columnNames.add(name);
				parameters.add(":" + name);
				updateParameters.add(name + "=:" + name);
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
	private List<Column> getColumns() {
		final List<Column> columns = new LinkedList<>();

		final Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			final Column col = method.getAnnotation(Column.class);
			if ((col != null) && (!EXCLUDE_FIELDS.contains(col.name()
																.toLowerCase()))) {
				columns.add(col);
			}
		}
		return Collections.unmodifiableList(columns);
	}

	private List<Object> getValues(final E entity, final List<String> names) throws IllegalStateException {
		Objects.requireNonNull(entity);

		final List<Object> values = new LinkedList<>();
		String getterName = "";

		for (String name : names) {
			try {
				getterName = new StringBuilder("get").append(name.substring(0, 1)
																	.toUpperCase())
														.append(name.substring(1, name.length())
																	.toLowerCase())
														.toString();
				final Object value = clazz.getMethod(getterName)
											.invoke(entity);
				values.add(value);
			} catch (Throwable e) {
				throw new IllegalArgumentException("Could not invoke '" + getterName + "'", e);
			}
		}
		return null;
	}

	public static void main(String args[]) {
		try {
			new ServiceDaoUtil<>(User.class);

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
