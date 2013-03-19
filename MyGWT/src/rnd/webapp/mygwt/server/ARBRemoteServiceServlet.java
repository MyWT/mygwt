package rnd.webapp.mygwt.server;

import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationResponse;
import rnd.mywt.server.arb.ARBImpl;
import rnd.webapp.mygwt.client.ARBRemoteService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ARBRemoteServiceServlet extends RemoteServiceServlet implements ARBRemoteService {

	private ARBImpl delegate = new ARBImpl();

	@Override
	public void init() {
		delegate.initialiseApplication();
	}

	@Override
	public ApplicationResponse executeRequest(ApplicationRequest req) {
		return delegate.executeRequest(req);
	}

}
