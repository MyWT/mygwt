package rnd.mywt.client.arb;

import rnd.mywt.client.rpc.ApplicationRequest;

public interface ARBAsync<T extends ARBAsyncCallback> {

	void executeRequest(ApplicationRequest req, T callback);

}
