package rnd.webapp.mygwt.client;

import com.google.gwt.core.client.GWT;

public class ARBRemoteServiceUtils {

	private static final ARBRemoteServiceAsync arb = GWT.create(ARBRemoteService.class);

	public static ARBRemoteServiceAsync getARB() {
		return arb;
	}

}
