package rnd.mywt.client.arb;

import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationResponse;

public interface ARB {

	ApplicationResponse executeRequest(ApplicationRequest req);

}
