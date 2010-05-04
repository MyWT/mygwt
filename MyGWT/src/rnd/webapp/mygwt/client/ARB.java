package rnd.webapp.mygwt.client;

import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationResponse;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ARB")
public interface ARB extends RemoteService {

	ApplicationResponse executeRequest(ApplicationRequest req);

}
