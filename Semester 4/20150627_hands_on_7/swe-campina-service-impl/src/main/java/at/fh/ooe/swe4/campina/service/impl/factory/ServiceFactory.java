package at.fh.ooe.swe4.campina.service.impl.factory;

import java.rmi.Remote;
import java.util.Objects;

import org.apache.log4j.Logger;

import at.fh.ooe.swe4.campina.service.api.spec.factory.RmiServiceFactory;

public class ServiceFactory implements RmiServiceFactory {

	private static final Logger	log				= Logger.getLogger(ServiceFactory.class);
	private static final String	IMPL_NAME_SPACE	= "at.fh.ooe.swe4.campina.service.impl.";

	public ServiceFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T extends Remote> T createService(Class<T> interfaze) {
		Objects.requireNonNull(interfaze);

		T instance = null;
		try {
			Class<T> clazz = (Class<T>) Class.forName(IMPL_NAME_SPACE + interfaze.getSimpleName() + "Impl");
			instance = clazz.newInstance();
		} catch (Throwable e) {
			log.error("Could create service instance", e);
		}

		return instance;
	}
}
