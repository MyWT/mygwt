package rnd.webapp.mygwt.client;

import java.io.Serializable;

import rnd.mywt.client.rpc.ApplicationResponse;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ARBAsyncCallback implements AsyncCallback<ApplicationResponse> {

	@Override
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
		Window.alert(caught.getMessage());
	}

	public void onSuccess(Serializable result) {
		// To be overrided by Requesters
	}

}
