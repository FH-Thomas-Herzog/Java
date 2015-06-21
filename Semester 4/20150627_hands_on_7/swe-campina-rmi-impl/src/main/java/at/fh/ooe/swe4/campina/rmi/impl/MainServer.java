package at.fh.ooe.swe4.campina.rmi.impl;

import at.fh.ooe.swe4.campina.persistence.impl.ConnectionManagerImpl.DbMetadata;
import at.fh.ooe.swe4.campina.persistence.impl.DbConfigParam;
import at.fh.ooe.swe4.campina.rmi.api.factory.RmiDaoFactory;
import at.fh.ooe.swe4.campina.rmi.api.rmi.RmiServer;

/**
 * This is the main RMI server which hosts the {@link RmiDaoFactory} for the
 * client applications.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 21, 2015
 */
public class MainServer {

	public MainServer() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Throwable {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		final DbMetadata metadata = new DbMetadata(DbConfigParam.DRIVER.val(),
				DbConfigParam.URL.val(),
				DbConfigParam.USER.val(),
				DbConfigParam.PASSWORD.val(),
				Integer.valueOf(DbConfigParam.ISOLATION.val()));
		final RmiDaoFactory serviceFactory = new RmiDaofactoryImpl(metadata);
		final RmiServer rmiServer = new RmiServerImpl(50555);
		rmiServer.start();
		rmiServer.bindBean(serviceFactory, RmiDaoFactory.class);
	}
}
