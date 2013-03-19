package rnd.webapp.mygwt.client;

import rnd.mywt.client.arb.ARB;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ARB")
public interface ARBRemoteService extends ARB, RemoteService {
}
