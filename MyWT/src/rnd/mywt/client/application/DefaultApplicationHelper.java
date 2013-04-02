package rnd.mywt.client.application;

import java.util.Collection;

import rnd.bean.ApplicationDynaBean;
import rnd.mywt.client.MyWTHelper;
import rnd.mywt.client.arb.ARBServiceResponseHandler;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.util.ARUtils;

public class DefaultApplicationHelper extends AbstractApplicationHelper implements ApplicationHelper {

	// private ApplicationHelper parentApplicationHelper;

	// public DefaultApplicationHelper(String applicationName, ApplicationHelper
	// parentApplicationHelper) {
	// super(applicationName);
	// this.parentApplicationHelper = parentApplicationHelper;
	// }

	public DefaultApplicationHelper(String applicationName) {
		super(applicationName);
	}

	@Override
	public void initialiseApplication() {

		ApplicationRequest loadReq = ARUtils.createLoadRequest("AD", "Application", applicationName);

		MyWTHelper.getARB().executeRequest(loadReq, new ARBServiceResponseHandler<ApplicationDynaBean>() {

			@Override
			public void processResult(ApplicationDynaBean app) {

				Collection<ApplicationDynaBean> modules = (Collection<ApplicationDynaBean>) app.getListValueReadOnly("module");
				for (ApplicationDynaBean module : modules) {

					DefaultModuleHelper moduleHelper = new DefaultModuleHelper((String) module.getValue("name"));

					Collection<ApplicationDynaBean> appBeans = (Collection<ApplicationDynaBean>) module.getListValueReadOnly("applicationBean");
					for (ApplicationDynaBean appBean : appBeans) {

						Collection<ApplicationDynaBean> forms = (Collection<ApplicationDynaBean>) appBean.getListValueReadOnly("form");
						for (ApplicationDynaBean form : forms) {

							MetadataFormHelper formHelper = new MetadataFormHelper((String) appBean.getValue("name"), (String) form.getValue("name"), (String) appBean.getValue("name"), form);
							moduleHelper.addFormHelper(formHelper);
						}
					}
					addModuleHelper(moduleHelper);
				}

				MyWTHelper.getHomePage().initializeFormAction(MyWTHelper.getApplicationHelper(), true);
				MyWTHelper.getHomePage().initializeFormAction(DefaultApplicationHelper.this, false);

			}

		});

	}
}
