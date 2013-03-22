package rnd.mywt.client;

import rnd.mywt.client.application.ApplicationHelper;
import rnd.mywt.client.arb.ARBAsync;
import rnd.mywt.client.mvc.MVCFactory;
import rnd.mywt.client.mvc.page.HomePage;

public class MyWTHelper {

	private static String applicationName;
	private static ApplicationHelper applicationHelper;
	private static ApplicationHelper defaultApplicationHelper;

	private static HomePage homePage;
	private static MVCFactory mvcFactory;
	private static ARBAsync arb;

	public static ARBAsync getARB() {
		return arb;
	}

	public static void setARB(ARBAsync arb) {
		MyWTHelper.arb = arb;
	}

	public static MVCFactory getMVCFactory() {
		return mvcFactory;
	}

	public static void setMVCFactory(MVCFactory handler) {
		MyWTHelper.mvcFactory = handler;
	}

	public static ApplicationHelper getApplicationHelper() {
		return applicationHelper;
	}

	public static void setApplicationHelper(ApplicationHelper applicationHelper) {
		MyWTHelper.applicationHelper = applicationHelper;
	}

	public static ApplicationHelper getDefaultApplicationHelper() {
		return defaultApplicationHelper;
	}

	public static void setDefaultApplicationHelper(ApplicationHelper defaultApplicationHelper) {
		MyWTHelper.defaultApplicationHelper = defaultApplicationHelper;
	}

	public static String getApplicationName() {
		return applicationName;
	}

	public static void setApplicationName(String applicationName) {
		MyWTHelper.applicationName = applicationName;
	}

	public static HomePage getHomePage() {
		return homePage;
	}

	public static void setHomePage(HomePage homePage) {
		MyWTHelper.homePage = homePage;
	}

}
