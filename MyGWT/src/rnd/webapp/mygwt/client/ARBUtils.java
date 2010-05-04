package rnd.webapp.mygwt.client;

import com.google.gwt.core.client.GWT;

public class ARBUtils {

	private static final ARBAsync arb = GWT.create(ARB.class);

	public static ARBAsync getARB() {
		return arb;
	}

}
