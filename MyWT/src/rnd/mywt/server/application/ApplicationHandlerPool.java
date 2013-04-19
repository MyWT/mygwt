package rnd.mywt.server.application;

import java.util.HashMap;
import java.util.Map;

public class ApplicationHandlerPool {

	private static Map<String, ApplicationHandler> pool = new HashMap<String, ApplicationHandler>();

	public static void registerApplicationHandler(String appName, ApplicationHandler applicationHandler) {
		pool.put(appName, applicationHandler);
	}

	public static ApplicationHandler getApplicationHandler(String appName) {
		ApplicationHandler applicationHandler = pool.get(appName);
		if (applicationHandler == null) {
			applicationHandler = new DefaultApplicationHandler(appName);
			pool.put(appName, applicationHandler);
		}
		return applicationHandler;
	}

}
