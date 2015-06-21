package at.fh.ooe.swe4.campina.test.dao.api;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.fh.ooe.swe4.campina.persistence.api.AbstractEntity;
import at.fh.ooe.swe4.campina.persistence.api.ConnectionManager;
import at.fh.ooe.swe4.campina.persistence.impl.ConnectionManagerImpl;
import at.fh.ooe.swe4.campina.persistence.impl.ConnectionManagerImpl.DbMetadata;
import at.fh.ooe.swe4.campina.persistence.impl.DbConfigParam;
import at.fh.ooe.swe4.campina.persistence.impl.EntityManagerImpl;
import at.fh.ooe.swe4.junit.test.suite.watcher.LoggingTestClassWatcher;
import at.fh.ooe.swe4.junit.test.suite.watcher.LoggingTestInvocationWatcher;

/**
 * This is the base class for all dao test.<br>
 * It provides the connection manager for the underlying test database.<br>
 * It provides utility methods for saving all types of entities and an
 * entitymanager for the tested entity dao.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 21, 2015
 * @param <T>
 */
@RunWith(JUnit4.class)
public abstract class AbstractDaoTest<T extends AbstractEntity> {

	protected final DbMetadata					dbMeta;
	protected final ConnectionManager			conManager;
	protected final EntityManagerImpl<T>		em;

	private final Logger						log;
	@ClassRule
	public static final LoggingTestClassWatcher	testClassWatcher		= new LoggingTestClassWatcher(Level.INFO);
	@Rule
	public final LoggingTestInvocationWatcher	testWatcher				= new LoggingTestInvocationWatcher(Level.INFO);
	@Rule
	public RemoteExceptionLogger				remoteExceptionLogger	= new RemoteExceptionLogger();
	@Rule
	public ExpectedException					expectedException		= ExpectedException.none();

	/**
	 * @param entityClazz
	 */
	public AbstractDaoTest(final Class<T> entityClazz) {
		super();
		log = Logger.getLogger(this.getClass());
		log.info("------------------------------------------");
		log.info("Initialization started");
		log.info("------------------------------------------");
		dbMeta = new DbMetadata(DbConfigParam.DRIVER.val(),
				DbConfigParam.URL.val(),
				DbConfigParam.USER.val(),
				DbConfigParam.PASSWORD.val(),
				2);
		this.conManager = new ConnectionManagerImpl(dbMeta);
		this.em = new EntityManagerImpl<>(entityClazz);
		setupDB();
		log.info("------------------------------------------");
		log.info("Initialization finished");
		log.info("------------------------------------------");
	}

	/**
	 * Drops and recreates the test database. Should be called before each test
	 * method invocation.
	 */
	protected void setupDB() {
		log.info("------------------------------------------");
		log.info("Initialization database started");
		log.info("ddl: db-config.xml");
		log.info("------------------------------------------");
		try {
			final List<String> lines = Files.readAllLines(Paths.get(this.getClass()
																		.getClassLoader()
																		.getResource("create-campina-schema.sql")
																		.toURI()), Charset.forName("UTF-8"));

			final List<String> statements = new ArrayList<>(lines.size());
			String statement = "";
			for (String string : lines) {
				statement += " " + string;
				if (string.endsWith(";")) {
					statements.add(statement);
					statement = "";
				}
			}
			try (final Connection con = conManager.getConnection(Boolean.FALSE)) {
				try (final PreparedStatement pstmt = con.prepareStatement("DROP SCHEMA IF EXISTS campina")) {
					pstmt.executeUpdate();
				}
				for (String string : statements) {
					log.info("Executing ddl: " + string);
					try (final PreparedStatement pstmt = con.prepareStatement(string)) {
						pstmt.executeUpdate();
					}
				}
			} catch (SQLException e) {
				throw new IllegalStateException("Could not setup db", e);
			}
		} catch (Throwable e) {
			log.error("Could not setup database", e);
			throw new IllegalStateException("ddl file load failed", e);
		}
		log.info("------------------------------------------");
		log.info("Initialization database finished");
		log.info("------------------------------------------");
	}

	/**
	 * Saves the collection of entities.
	 * 
	 * @param entities
	 *            the entities to be saved
	 * @return the saved entities
	 */
	protected <E extends AbstractEntity> List<E> saveEntities(final List<E> entities) {
		Objects.requireNonNull(entities);
		final List<E> saved = new ArrayList<>(entities.size());

		for (E entity : entities) {
			saved.add(saveEntity(entity));
		}

		return saved;
	}

	/**
	 * Saves the given entity.
	 * 
	 * @param entity
	 *            the entity to be saved.
	 * @return the saved entity
	 */
	@SuppressWarnings({
						"rawtypes", "unchecked" })
	protected <E extends AbstractEntity> E saveEntity(final E entity) {
		Objects.requireNonNull(entity);

		E entityDB;
		try (final Connection con = conManager.getConnection(Boolean.FALSE)) {
			entityDB = (E) (new EntityManagerImpl(entity.getClass())).saveOrUpdate(con, entity);
		} catch (SQLException e) {
			throw new IllegalStateException("Entity save failed", e);
		}

		return entityDB;
	}
}
