package rnd.webapp.mygwtext.client.tree;

import com.gwtext.client.data.Node;

public class NodeUtils implements NodeConstants {

	public static String getType(Node node) {
		return node.getAttribute(TYPE);
	}

	public static boolean isFormNode(Node node) {
		return node.getAttribute(TYPE).equals(TYPE_FORM);
	}

	public static boolean isTypeModule(Node node) {
		return node.getAttribute(TYPE).equals(TYPE_MODULE);
	}

	public static String getViewName(Node node) {
		return node.getAttribute(VIEW_NAME);
	}

	public static String getFormName(Node node) {
		return node.getAttribute(FORM_NAME);
	}

	public static String getAppBeanName(Node node) {
		return node.getAttribute(APP_BEAN_NAME);
	}

	public static String getModuleName(Node node) {
		return node.getAttribute(MODULE_NAME);
	}

	public static void setTypeForm(Node node) {
		node.setAttribute(TYPE, TYPE_FORM);
	}

	public static void setTypeModule(Node node) {
		node.setAttribute(TYPE, TYPE_MODULE);
	}

	public static void setViewName(Node node, String viewName) {
		node.setAttribute(VIEW_NAME, viewName);
	}

	public static void setFormName(Node node, String formName) {
		node.setAttribute(FORM_NAME, formName);
	}

	public static void setAppBeanName(Node node, String appBeanName) {
		node.setAttribute(APP_BEAN_NAME, appBeanName);
	}

	public static void setModuleName(Node node, String moduleName) {
		node.setAttribute(MODULE_NAME, moduleName);
	}

}
