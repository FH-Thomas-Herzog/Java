package at.fh.ooe.swe4.campina.dao.impl;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import at.fh.ooe.swe4.campina.dao.api.AbstractRemoteDao;
import at.fh.ooe.swe4.campina.dao.api.OrderDao;
import at.fh.ooe.swe4.campina.persistence.api.ConnectionManager;
import at.fh.ooe.swe4.campina.persistence.api.EntityManager;
import at.fh.ooe.swe4.campina.persistence.api.entity.Menu;
import at.fh.ooe.swe4.campina.persistence.api.entity.MenuEntry;
import at.fh.ooe.swe4.campina.persistence.api.entity.Order;
import at.fh.ooe.swe4.campina.persistence.api.entity.User;
import at.fh.ooe.swe4.campina.persistence.impl.EntityManagerImpl;

/**
 * This is the implementation of the {@link OrderDao} specification.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 21, 2015
 */
public class OrderDaoImpl extends AbstractRemoteDao implements OrderDao {

	private final EntityManager<Order>		orderEm;
	private final EntityManager<User>		userEm;
	private final EntityManager<MenuEntry>	menuEntryEm;
	private final EntityManager<Menu>		menuEm;

	private static final String				SELECT_QUERY		= "SELECT %s FROM campina.order o "
																		+ "INNER JOIN campina.user u ON u.id = o.user_id "
																		+ "INNER JOIN campina.menu_entry me ON me.id = o.menu_entry_id "
																		+ "INNER JOIN campina.menu m ON m.id = me.menu_id "
																		+ "ORDER BY order_date DESC ";
	private static final long				serialVersionUID	= -247051344150973512L;

	/**
	 * @param connectionManager
	 * @throws RemoteException
	 */
	public OrderDaoImpl(ConnectionManager connectionManager) throws RemoteException {
		super(connectionManager);
		this.orderEm = new EntityManagerImpl<>(Order.class);
		this.userEm = new EntityManagerImpl<>(User.class);
		this.menuEm = new EntityManagerImpl<>(Menu.class);
		this.menuEntryEm = new EntityManagerImpl<>(MenuEntry.class);
	}

	@Override
	public Order save(Order order) throws RemoteException {
		if (order == null) {
			throw new RemoteException("Cannot save or update null entity", new NullPointerException());
		}

		try (final Connection con = connectionManager.getConnection(Boolean.TRUE)) {
			order = orderEm.saveOrUpdate(con, order);
			con.commit();
			return order;
		} catch (Throwable e) {
			throw new RemoteException("Could not save or update menu", e);
		}
	}

	@Override
	public void delete(Order entity) throws RemoteException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Order byId(Integer id) throws RemoteException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Order> getAll() throws RemoteException {
		String names = "o.id, " + orderEm.getColumnNames("o") + ", o.version, ";
		names += "me.id, " + menuEntryEm.getColumnNames("me") + ", me.version, ";
		names += "m.id, " + menuEm.getColumnNames("m") + ", m.version, ";
		names += "u.id, " + userEm.getColumnNames("u") + ", u.version";

		System.out.println(String.format(SELECT_QUERY, names));
		final List<Order> orders = new ArrayList<>();
		try (final Connection con = connectionManager.getConnection(Boolean.FALSE)) {
			try (final PreparedStatement pstmt = con.prepareStatement(String.format(SELECT_QUERY, names))) {
				final ResultSet result = pstmt.executeQuery();

				while (result.next()) {
					final Order order = new Order();
					final MenuEntry menuEntry = new MenuEntry();
					final Menu menu = new Menu();
					final User user = new User();
					int colIdx = 1;

					order.setId(result.getInt(colIdx));
					orderEm.fillEntity(result, order, colIdx);
					order.setVersion(result.getLong((colIdx = orderEm.getColumnMeta()
																		.size() + 2)));

					menuEntry.setId(result.getInt((colIdx += 1)));
					menuEntryEm.fillEntity(result, menuEntry, colIdx);
					menuEntry.setVersion(result.getLong((colIdx = colIdx + menuEntryEm.getColumnMeta()
																						.size() + 1)));

					menu.setId(result.getInt((colIdx += 1)));
					menuEm.fillEntity(result, menu, colIdx);
					menu.setVersion(result.getLong((colIdx = colIdx + menuEm.getColumnMeta()
																			.size() + 1)));

					user.setId(result.getInt((colIdx += 1)));
					userEm.fillEntity(result, user, colIdx);
					user.setVersion(result.getLong((colIdx = colIdx + userEm.getColumnMeta()
																			.size() + 1)));

					menuEntry.setMenu(menu);
					order.setUser(user);
					order.setMenuEntry(menuEntry);

					orders.add(order);
				}
			}
		} catch (Throwable e) {
			throw new RemoteException("Could not load all orders", e);
		}

		return orders;
	}
}
