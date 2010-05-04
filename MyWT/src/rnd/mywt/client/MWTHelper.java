package rnd.mywt.client;

import rnd.mywt.client.application.ApplicationHelper;

public class MWTHelper {

	private static ApplicationHelper applicationHelper;

	public static ApplicationHelper getApplicationHelper() {
		return applicationHelper;
	}

	public static void setApplicationHelper(ApplicationHelper applicationHelper) {
		MWTHelper.applicationHelper = applicationHelper;
	}

}
