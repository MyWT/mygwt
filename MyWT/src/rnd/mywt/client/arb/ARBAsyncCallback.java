package rnd.mywt.client.arb;

import java.io.Serializable;

import rnd.mywt.client.rpc.ApplicationResponse;

public class ARBAsyncCallback {

	public void onSuccess(ApplicationResponse resp) {
		Throwable throwable = resp.getThrowable();
		if (throwable != null) {
			onFailure(throwable);
			// throw new RuntimeException(throwable);
		} else {
			onSuccess(resp.getResult());
		}
	}

	public void onFailure(Throwable caught) {
		caught.printStackTrace();
		//Window.alert(caught.getMessage());
	}

	public void onSuccess(Serializable result) {
		// To be overrided by Requesters
	}

}
