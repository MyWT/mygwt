package rnd.mywt.client.rpc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ApplicationRequest implements Serializable {

	public static enum Method {
		Create, Save, Fetch, Find, Delete
	}

	private String moduleName;

	private Method method;

	Map<String, Serializable> params = new HashMap(0);

	public ApplicationRequest() {
	}

	public ApplicationRequest(String moduleName, Method method) {
		setModuleName(moduleName);
		setMethod(method);
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public void setParam(String paramName, Serializable paramValue) {
		this.params.put(paramName, paramValue);
	}

	public Serializable getParam(String paramName) {
		return this.params.get(paramName);
	}

	public String getModule() {
		return this.moduleName;
	}

	public Method getMethod() {
		return this.method;
	}

	public Map getParams() {
		return this.params;
	}

}
