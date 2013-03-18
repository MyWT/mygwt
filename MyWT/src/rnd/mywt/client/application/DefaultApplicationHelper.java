package rnd.mywt.client.application;

import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.util.ARUtils;

public class DefaultApplicationHelper extends AbstractApplicationHelper implements ApplicationHelper {

	public DefaultApplicationHelper(String applicationName) {
		super(applicationName);
	}

	@Override
	public void initialiseApplication() {

		ApplicationRequest loadReq = ARUtils.createLoadRequest("AD", "Module", applicationName);

	}

}
