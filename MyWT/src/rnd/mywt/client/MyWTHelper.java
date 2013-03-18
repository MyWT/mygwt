package rnd.mywt.client;

import rnd.mywt.client.application.ApplicationHelper;

public class MyWTHelper {

	private static ApplicationHelper applicationHelper;
	private static String applicationName;

	public static ApplicationHelper getApplicationHelper() {
		return applicationHelper;
	}

	public static void setApplicationHelper(ApplicationHelper applicationHelper) {
		MyWTHelper.applicationHelper = applicationHelper;
	}

	public static String getApplicationName() {
		return applicationName;
	}

	public static void setApplicationName(String applicationName) {
		MyWTHelper.applicationName = applicationName;
	}

}
