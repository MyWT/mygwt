package rnd.webapp.mygwt.client;

import com.google.gwt.core.client.GWT;

import rnd.mywt.client.arb.ARBServiceProvider;
import rnd.mywt.client.arb.ARBServiceResponseHandler;
import rnd.mywt.client.rpc.ApplicationRequest;

public class ARBRemoteServiceProvider implements ARBServiceProvider<ARBServiceResponseHandler> {

	private static final ARBRemoteServiceAsync arb = GWT.create(ARBRemoteService.class);

	@Override
	public void executeRequest(ApplicationRequest req, ARBServiceResponseHandler callback) {
		arb.executeRequest(req, new ARBRemoteServiceResponseHandler(callback));
	}

}
