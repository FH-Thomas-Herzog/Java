package at.fh.ooe.swe4.campina.test.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.fh.ooe.swe4.campina.dao.api.OrderDao;
import at.fh.ooe.swe4.campina.dao.impl.OrderDaoImpl;
import at.fh.ooe.swe4.campina.persistence.api.entity.Menu;
import at.fh.ooe.swe4.campina.persistence.api.entity.MenuEntry;
import at.fh.ooe.swe4.campina.persistence.api.entity.Order;
import at.fh.ooe.swe4.campina.persistence.api.entity.User;
import at.fh.ooe.swe4.campina.persistence.api.entity.constants.Day;
import at.fh.ooe.swe4.campina.test.dao.api.AbstractDaoTest;
import at.fh.ooe.swe4.campina.test.dao.api.RemoteDetailMatcher;

/**
 * This test class represents the test class for the {@link OrderDao}
 * implementation.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 20, 2015
 */
public class OrderDaoTest extends AbstractDaoTest<Order> {

	private final OrderDao	dao;

	public OrderDaoTest() throws RemoteException {
		super(Order.class);
		this.dao = new OrderDaoImpl(conManager);
	}

	@Before
	public void beforeTest() {
		setupDB();
	}

	@After
	public void afterTest() {
		setupDB();
	}

	@Test
	public void saveNull() throws RemoteException {
		// -- Given --
		final Order order = null;
		expectedException.expect(new RemoteDetailMatcher(NullPointerException.class));

		// -- When | Then --
		dao.save(order);
	}

	@Test
	public void save() throws RemoteException {
		// -- Given --
		User user = new User();
		user.setFirstName("Thomas-");
		user.setLastName("Herzog-");
		user.setUsername("cchet-");
		user.setEmail("t.t@t.at");
		user.setPassword("xxxxxxx");
		user.setAdminFlag(Boolean.TRUE);
		user.setBlockedFlag(Boolean.FALSE);
		user = saveEntity(user);

		Menu menu = new Menu();
		menu.setDay(Day.MONDAY);
		menu.setLabel("menu-1");
		menu = saveEntity(menu);

		MenuEntry menuEntry = new MenuEntry();
		menuEntry.setLabel("menu--entry-1");
		menuEntry.setMenu(menu);
		menuEntry.setOrdinal(1);
		menuEntry.setPrice(BigDecimal.ONE);
		menuEntry = saveEntity(menuEntry);

		Order order = new Order();
		order.setMenuEntry(menuEntry);
		order.setOrderDate(Calendar.getInstance());
		order.setCollectDate(Calendar.getInstance());
		order.setUser(user);
		order = saveEntity(order);

		// -- When --
		order = dao.save(order);

		// -- Then --
		try (final Connection con = conManager.getConnection(Boolean.TRUE);) {
			final Order orderDB = em.byId(con, user.getId());
			assertEquals(order, orderDB);
		} catch (SQLException e) {
			fail("Could not obtain connection");
		}
	}

	@Test
	public void getAllEmpty() throws RemoteException {
		// -- Given | When --
		final List<Order> fetchedOrders = dao.getAll();

		// -- Then --
		assertTrue(fetchedOrders.isEmpty());
	}

	@Test
	public void getAll() throws RemoteException {
		// -- Given --
		List<Order> orders = new ArrayList<>(5);
		User user = new User();
		user.setFirstName("Thomas-");
		user.setLastName("Herzog-");
		user.setUsername("cchet-");
		user.setEmail("t.t@t.at");
		user.setPassword("xxxxxxx");
		user.setAdminFlag(Boolean.TRUE);
		user.setBlockedFlag(Boolean.FALSE);
		user = saveEntity(user);

		Menu menu = new Menu();
		menu.setDay(Day.MONDAY);
		menu.setLabel("menu-1");
		menu = saveEntity(menu);

		MenuEntry menuEntry = new MenuEntry();
		menuEntry.setLabel("menu--entry-1");
		menuEntry.setMenu(menu);
		menuEntry.setOrdinal(1);
		menuEntry.setPrice(BigDecimal.ONE);
		menuEntry = saveEntity(menuEntry);

		for (int i = 0; i < 10; i++) {
			final Order order = new Order();
			order.setMenuEntry(menuEntry);
			order.setOrderDate(Calendar.getInstance());
			order.setCollectDate(Calendar.getInstance());
			order.setUser(user);
		}
		orders = saveEntities(orders);

		// -- When --
		final List<Order> fetchedOrders = dao.getAll();

		// -- Then --
		assertEquals(orders.size(), fetchedOrders.size());
		assertEquals(orders, fetchedOrders);
		for (Order order : fetchedOrders) {
			assertEquals(order.getUser(), user);
			assertEquals(order.getMenuEntry(), menuEntry);
			assertEquals(order.getMenuEntry()
								.getMenu(), menu);
		}
	}
}
