package rnd.mywt.client.arb;

import java.io.Serializable;

import rnd.mywt.client.rpc.ApplicationResponse;

public class ARBAsyncCallback<T extends Serializable> {

	public final void onSuccess(ApplicationResponse resp) {
		Throwable throwable = resp.getThrowable();
		if (throwable != null) {
			onFailure(throwable);
			// throw new RuntimeException(throwable);
		} else {
			processResult((T) resp.getResult());
		}
	}

	public void onFailure(Throwable caught) {
		caught.printStackTrace();
		// Window.alert(caught.getMessage());
	}

	public void processResult(T result) {
		// To be overrided by Requesters
	}

}
