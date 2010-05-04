package rnd.webapp.mygwt.client;

import java.io.Serializable;

import rnd.mywt.client.rpc.ApplicationResponse;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class ARBAsyncCallback implements AsyncCallback {

	public void onFailure(Throwable caught) {
		caught.printStackTrace();
		Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(Object result) {
		onSuccess(getSerializableResult(result));
	}

	public abstract void onSuccess(Serializable result);

	public Serializable getSerializableResult(Object result) {
		ApplicationResponse resp = (ApplicationResponse) result;
		Throwable throwable = resp.getThrowable();
		if (throwable != null) {
			onFailure(throwable);
			throw new RuntimeException(throwable);
		} else {
			return resp.getResult();
		}
	}

}
