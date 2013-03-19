package rnd.webapp.mygwt.client;

import rnd.mywt.client.arb.ARBAsync;
import rnd.mywt.client.arb.ARBAsyncCallback;
import rnd.mywt.client.rpc.ApplicationRequest;

public class ARBRemoteServiceProvider implements ARBAsync {

	@Override
	public void executeRequest(ApplicationRequest req, ARBAsyncCallback callback) {
		ARBRemoteServiceUtils.getARB().executeRequest(req, new ARBRemoteServiceAsyncCallback(callback));
	}

}
