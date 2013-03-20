package rnd.mywt.client;

import rnd.mywt.client.application.ApplicationHelper;
import rnd.mywt.client.arb.ARBAsync;
import rnd.mywt.client.mvc.MVCHandler;
import rnd.mywt.client.mvc.page.HomePage;

public class MyWTHelper {

	private static String applicationName;
	private static ApplicationHelper applicationHelper;
	private static HomePage homePage;
	private static MVCHandler sharedHandler;
	private static ARBAsync arb;

	public static ARBAsync getARB() {
		return arb;
	}

	public static void setARB(ARBAsync arb) {
		MyWTHelper.arb = arb;
	}

	public static MVCHandler getMVCHandler() {
		return sharedHandler;
	}

	public static void setMVCHandler(MVCHandler handler) {
		MyWTHelper.sharedHandler = handler;
	}

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

	public static HomePage getHomePage() {
		return homePage;
	}

	public static void setHomePage(HomePage homePage) {
		MyWTHelper.homePage = homePage;
	}

}
