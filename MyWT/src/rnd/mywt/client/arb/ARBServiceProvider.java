package rnd.mywt.client.arb;

import rnd.mywt.client.rpc.ApplicationRequest;

public interface ARBServiceProvider<T extends ARBServiceResponseHandler> {

	void executeRequest(ApplicationRequest req, T callback);

}
