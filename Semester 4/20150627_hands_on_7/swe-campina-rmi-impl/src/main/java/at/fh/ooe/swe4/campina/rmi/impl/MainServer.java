package at.fh.ooe.swe4.campina.rmi.impl;

import java.io.Serializable;
import java.rmi.RMISecurityManager;

import at.fh.ooe.swe4.campina.persistence.impl.ConnectionManagerImpl.DbMetadata;
import at.fh.ooe.swe4.campina.persistence.impl.DbConfigParam;
import at.fh.ooe.swe4.campina.rmi.api.factory.RmiServiceFactory;
import at.fh.ooe.swe4.campina.rmi.api.rmi.RmiServer;

public class MainServer implements Serializable {

	public MainServer() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Throwable {

		// System.setProperty("java.rmi.server.codebase",
		// ServiceFactory.class.getProtectionDomain()
		// .getCodeSource()
		// .getLocation()
		// .toString());
		//
		// System.setProperty("java.security.policy", "/java.policy");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		final DbMetadata metadata = new DbMetadata(DbConfigParam.DRIVER.val(),
				DbConfigParam.URL.val(),
				DbConfigParam.USER.val(),
				DbConfigParam.PASSWORD.val(),
				Integer.valueOf(DbConfigParam.ISOLATION.val()));
		final RmiServiceFactory serviceFactory = new ServiceFactory(metadata);
		final RmiServer rmiServer = new RmiServerImpl(50555);
		rmiServer.start();
		rmiServer.bindBean(serviceFactory, RmiServiceFactory.class);
	}
}
