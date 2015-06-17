package at.fh.ooe.swe4.campina.service.impl;

import java.io.Serializable;
import java.rmi.RMISecurityManager;

import at.fh.ooe.swe4.campina.service.api.spec.factory.RmiServiceFactory;
import at.fh.ooe.swe4.campina.service.api.spec.rmi.RmiServer;
import at.fh.ooe.swe4.campina.service.impl.factory.ServiceFactory;
import at.fh.ooe.swe4.campina.service.impl.rmi.RmiServerImpl;

public class Main implements Serializable {

	public Main() {
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
		final RmiServiceFactory serviceFactory = new ServiceFactory();
		final RmiServer rmiServer = new RmiServerImpl(50555);
		rmiServer.start();
		rmiServer.bindBean(serviceFactory, RmiServiceFactory.class);
	}
}
