package rnd.mywt.client.rpc;

public class DefaultApplicationRequest {// implements ApplicationRequest {
	// public class DefaultApplicationRequest extends AbstractBean implements ApplicationRequest {

	String moduleName;

	String requestMethod;

	Object[] requestParam;

	public String setModuleName(String moduleName) {
		// return (String) setValue(MODULE_NAME_PRP_NAME, moduleName);
		return this.moduleName = moduleName;
	}

	public String setRequestMethod(String requestMethod) {
		// return (String) setValue(REQ_METHOD_PRP_NAME, requestMethod);
		return this.requestMethod = requestMethod;
	}

	public Object[] setRequestParam(Object[] requestParam) {
		// return (Object[]) setValue(REQ_PARAM_PRP_NAME, requestParam);
		return this.requestParam = requestParam;
	}

	public String getModuleName() {
		// return (String) getValue(MODULE_NAME_PRP_NAME);
		return this.moduleName;
	}

	public String getRequestMethod() {
		// return (String) getValue(REQ_METHOD_PRP_NAME);
		return this.requestMethod;
	}

	public Object[] getRequestParam() {
		// return (Object[]) getValue(REQ_PARAM_PRP_NAME);
		return this.requestParam;
	}

	public boolean isBackground() {
		return false;
	}

}
