package rnd.mywt.client.rpc;

import java.io.Serializable;

public class ApplicationResponse implements Serializable {

	private Serializable result;

	private Throwable throwable;

	public ApplicationResponse() {
	}

	public ApplicationResponse(Serializable result) {
		this.result = result;
	}

	public void setResult(Serializable result) {
		this.result = result;
	}

	public Serializable getResult() {
		return result;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public Throwable getThrowable() {
		return throwable;
	}

}
