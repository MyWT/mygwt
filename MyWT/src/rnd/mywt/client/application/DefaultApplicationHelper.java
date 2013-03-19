package rnd.mywt.client.application;

import java.util.Collection;

import rnd.mywt.client.arb.ARBAsyncCallback;
import rnd.mywt.client.arb.ARBUtils;
import rnd.mywt.client.bean.ApplicationDynaBean;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationResponse;
import rnd.mywt.client.rpc.util.ARUtils;

public class DefaultApplicationHelper extends AbstractApplicationHelper implements ApplicationHelper {

	public DefaultApplicationHelper(String applicationName) {
		super(applicationName);
	}

	@Override
	public void initialiseApplication() {

		ApplicationRequest loadReq = ARUtils.createLoadRequest("AD", "Module", applicationName);

		ARBUtils.getARB().executeRequest(loadReq, new ARBAsyncCallback() {

			@Override
			public void onSuccess(ApplicationResponse resp) {
				super.onSuccess(resp);
				Collection<ApplicationDynaBean> modules = (Collection<ApplicationDynaBean>) resp.getResult();

				for (ApplicationDynaBean module : modules) {
					addModuleHelper(new DefaultModuleHelper((String) module.getValue("name")));
				}
			}

		});

	}
}
