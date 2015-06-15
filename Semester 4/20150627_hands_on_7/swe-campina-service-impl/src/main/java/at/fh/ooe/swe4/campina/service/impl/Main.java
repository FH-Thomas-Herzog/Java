package at.fh.ooe.swe4.campina.service.impl;

import java.io.Serializable;
import java.rmi.Naming;
import java.util.Objects;

import at.fh.ooe.swe4.campina.service.api.LoginEventService;
import at.fh.ooe.swe4.campina.service.api.spec.factory.RmiServiceFactory;
import at.fh.ooe.swe4.campina.service.api.spec.rmi.RmiServer;
import at.fh.ooe.swe4.campina.service.impl.factory.ServiceFactory;
import at.fh.ooe.swe4.campina.service.impl.rmi.RmiServerImpl;

public class Main implements Serializable {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Throwable {
		final RmiServiceFactory serviceFactory = new ServiceFactory();
		final RmiServer rmiServer = new RmiServerImpl(50555);
		rmiServer.start();
		rmiServer.bindService(serviceFactory.createService(LoginEventService.class), LoginEventService.class);

		rmiServer.stop();
	}
}
