package rnd.mywt.client;

import rnd.mywt.client.application.ApplicationHelper;
import rnd.mywt.client.arb.ARBServiceProvider;
import rnd.mywt.client.mvc.MVCBeanFactory;
import rnd.mywt.client.mvc.page.HomePage;

public class MyWTHelper {

	private static ApplicationHelper applicationHelper;

	private static HomePage homePage;
	private static MVCBeanFactory mvcFactory;
	private static ARBServiceProvider arb;

	public static ARBServiceProvider getARB() {
		return arb;
	}

	public static void setARB(ARBServiceProvider arb) {
		MyWTHelper.arb = arb;
	}

	public static MVCBeanFactory getMVCFactory() {
		return mvcFactory;
	}

	public static void setMVCFactory(MVCBeanFactory handler) {
		MyWTHelper.mvcFactory = handler;
	}

	public static ApplicationHelper getApplicationHelper() {
		return applicationHelper;
	}

	public static void setApplicationHelper(ApplicationHelper applicationHelper) {
		MyWTHelper.applicationHelper = applicationHelper;
	}

	public static HomePage getHomePage() {
		return homePage;
	}

	public static void setHomePage(HomePage homePage) {
		MyWTHelper.homePage = homePage;
	}

}
