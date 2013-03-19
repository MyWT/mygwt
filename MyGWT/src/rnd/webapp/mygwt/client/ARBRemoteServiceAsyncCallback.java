package rnd.webapp.mygwt.client;

import rnd.mywt.client.arb.ARBAsyncCallback;
import rnd.mywt.client.rpc.ApplicationResponse;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ARBRemoteServiceAsyncCallback implements AsyncCallback<ApplicationResponse> {

	private ARBAsyncCallback delegate;

	public ARBRemoteServiceAsyncCallback(ARBAsyncCallback delegate) {
		this.delegate = delegate;
	}

	public void onFailure(Throwable caught) {
		delegate.onFailure(caught);
		Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(ApplicationResponse resp) {
		delegate.onSuccess(resp);
	}

}
