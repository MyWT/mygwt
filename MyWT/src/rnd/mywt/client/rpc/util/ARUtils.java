package rnd.mywt.client.rpc.util;

import java.io.Serializable;

import rnd.mywt.client.bean.ApplicationBean;
import rnd.mywt.client.data.FilterInfo;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationRequest.Method;

public class ARUtils {

	private static final String APP_BEAN_NAME = "applicationBeanName";

	private static final String APP_BEAN_PK_ID = "applicationBeanPKId";

	private static final String APP_BEAN = "applicationBean";

	private static final String VIEW_NAME = "viewName";

	private static final String FILTER = "filterInfo";
	
	public static void setAppBeanName(ApplicationRequest request, String appBeanName) {
		request.setParam(APP_BEAN_NAME, appBeanName);
	}

	public static String getAppBeanName(ApplicationRequest request) {
		return (String) request.getParam(APP_BEAN_NAME);
	}

	public static void setAppBeanPKId(ApplicationRequest request, Long appBeanPKId) {
		request.setParam(APP_BEAN_PK_ID, appBeanPKId);
	}

	public static Long getAppBeanPKId(ApplicationRequest request) {
		return (Long) request.getParam(APP_BEAN_PK_ID);
	}

	public static void setApplicationBean(ApplicationRequest request, ApplicationBean appBean) {
		request.setParam(APP_BEAN, (Serializable) appBean);
	}

	public static ApplicationBean getApplicationBean(ApplicationRequest request) {
		return (ApplicationBean) request.getParam(APP_BEAN);
	}

	public static void setViewName(ApplicationRequest request, String viewName) {
		request.setParam(VIEW_NAME, viewName);
	}

	public static String getViewName(ApplicationRequest request) {
		return (String) request.getParam(VIEW_NAME);
	}

	public static void setFilter(ApplicationRequest request, FilterInfo filterInfo) {
		request.setParam(FILTER, (Serializable) filterInfo);
	}

	public static FilterInfo getFilter(ApplicationRequest request) {
		return (FilterInfo) request.getParam(FILTER);
	}
	
	public static ApplicationRequest createSaveRequest(String moduleName, String appBeanName, String viewName, ApplicationBean appBean) {
		ApplicationRequest request = new ApplicationRequest(moduleName, Method.Save);
		ARUtils.setAppBeanName(request, appBeanName);
		ARUtils.setViewName(request, viewName);
		ARUtils.setApplicationBean(request, appBean);
		return request;
	}

	public static ApplicationRequest createFetchRequest(String moduleName, String appBeanName, String viewName, FilterInfo filterInfo) {
		ApplicationRequest request = new ApplicationRequest(moduleName, Method.Fetch);
		ARUtils.setAppBeanName(request, appBeanName);
		ARUtils.setViewName(request, viewName);
		ARUtils.setFilter(request, filterInfo);
		return request;
	}

	public static ApplicationRequest createFindRequest(String moduleName, String appBeanName, Long appBeanId) {
		ApplicationRequest request = new ApplicationRequest(moduleName, Method.Find);
		ARUtils.setAppBeanName(request, appBeanName);
		ARUtils.setAppBeanPKId(request, appBeanId);
		return request;
	}

	public static ApplicationRequest createDeleteRequest(String moduleName, String appBeanName, Long appBeanId) {
		ApplicationRequest request = new ApplicationRequest(moduleName, Method.Delete);
		ARUtils.setAppBeanName(request, appBeanName);
		ARUtils.setAppBeanPKId(request, appBeanId);
		return request;
	}

}
