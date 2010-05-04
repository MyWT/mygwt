package rnd.mywt.client.rpc;

public class DefaultApplicationResponse {// implements ApplicationResponse {
	// public class DefaultApplicationResponse extends AbstractBean implements ApplicationResponse {
	Object result;

	public Object getResult() {
		// return getValue(RESULT_PRP_NAME);
		return this.result;
	}

	public void setResult(Object result) {
		// setValue(RESULT_PRP_NAME, result);
		this.result = result;
	}

}
