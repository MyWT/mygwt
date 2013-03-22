package rnd.webapp.mygwt.client;

import com.google.gwt.core.client.GWT;

import rnd.mywt.client.arb.ARBAsync;
import rnd.mywt.client.arb.ARBAsyncCallback;
import rnd.mywt.client.rpc.ApplicationRequest;

public class ARBRemoteServiceProvider implements ARBAsync<ARBAsyncCallback> {

	private static final ARBRemoteServiceAsync arb = GWT.create(ARBRemoteService.class);

	@Override
	public void executeRequest(ApplicationRequest req, ARBAsyncCallback callback) {
		arb.executeRequest(req, new ARBRemoteServiceAsyncCallback(callback));
	}

}
