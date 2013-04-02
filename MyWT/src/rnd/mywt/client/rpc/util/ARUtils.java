package rnd.mywt.client.rpc.util;

import java.io.Serializable;

import rnd.bean.ApplicationBean;
import rnd.mywt.client.data.FilterInfo;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationRequest.Method;

public class ARUtils {

	private static final String APP_BEAN_NAME = "applicationBeanName";

	private static final String APP_BEAN_PK_ID = "applicationBeanPKId";

	private static final String APP_BEAN = "applicationBean";

	private static final String VIEW_NAME = "viewName";

	private static final String FILTER = "filterInfo";

	private static final String PARENT = "parent";
	
	private static final String NAME = "name";

	public static void setAppBeanName(ApplicationRequest req, String appBeanName) {
		req.setParam(APP_BEAN_NAME, appBeanName);
	}

	public static String getAppBeanName(ApplicationRequest req) {
		return (String) req.getParam(APP_BEAN_NAME);
	}

	public static void setAppBeanPKId(ApplicationRequest req, Long appBeanPKId) {
		req.setParam(APP_BEAN_PK_ID, appBeanPKId);
	}

	public static Long getAppBeanPKId(ApplicationRequest req) {
		return (Long) req.getParam(APP_BEAN_PK_ID);
	}

	public static void setApplicationBean(ApplicationRequest req, ApplicationBean appBean) {
		req.setParam(APP_BEAN, (Serializable) appBean);
	}

	public static ApplicationBean getApplicationBean(ApplicationRequest req) {
		return (ApplicationBean) req.getParam(APP_BEAN);
	}

	public static void setViewName(ApplicationRequest req, String viewName) {
		req.setParam(VIEW_NAME, viewName);
	}

	public static String getViewName(ApplicationRequest req) {
		return (String) req.getParam(VIEW_NAME);
	}

	public static void setFilter(ApplicationRequest req, FilterInfo filterInfo) {
		req.setParam(FILTER, (Serializable) filterInfo);
	}

	public static FilterInfo getFilter(ApplicationRequest req) {
		return (FilterInfo) req.getParam(FILTER);
	}

	public static void setParent(ApplicationRequest req, String parentName) {
		req.setParam(PARENT, parentName);
	}

	public static String getParent(ApplicationRequest req) {
		return (String) req.getParam(PARENT);
	}
	
	public static void setName(ApplicationRequest req, String name) {
		req.setParam(NAME, name);
	}

	public static String getName(ApplicationRequest req) {
		return (String) req.getParam(NAME);
	}

	public static ApplicationRequest createSaveRequest(String moduleName, String appBeanName, String viewName, ApplicationBean appBean) {
		ApplicationRequest req = new ApplicationRequest(moduleName, Method.Save);
		setAppBeanName(req, appBeanName);
		setViewName(req, viewName);
		setApplicationBean(req, appBean);
		return req;
	}

	public static ApplicationRequest createFetchRequest(String moduleName, String appBeanName, String viewName, FilterInfo filterInfo) {
		ApplicationRequest req = new ApplicationRequest(moduleName, Method.Fetch);
		setAppBeanName(req, appBeanName);
		setViewName(req, viewName);
		setFilter(req, filterInfo);
		return req;
	}

	public static ApplicationRequest createFindRequest(String moduleName, String appBeanName, Long appBeanId) {
		ApplicationRequest req = new ApplicationRequest(moduleName, Method.Find);
		setAppBeanName(req, appBeanName);
		setAppBeanPKId(req, appBeanId);
		return req;
	}

	public static ApplicationRequest createDeleteRequest(String moduleName, String appBeanName, Long appBeanId) {
		ApplicationRequest req = new ApplicationRequest(moduleName, Method.Delete);
		setAppBeanName(req, appBeanName);
		setAppBeanPKId(req, appBeanId);
		return req;
	}

	public static ApplicationRequest createLoadRequest(String moduleName, String appBeanName, String name) {
		ApplicationRequest req = new ApplicationRequest(moduleName, Method.Load);
		setAppBeanName(req, appBeanName);
		setName(req, name);
		return req;

	}

}
