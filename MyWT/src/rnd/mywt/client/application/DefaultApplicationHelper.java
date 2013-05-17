package rnd.mywt.client.application;

import java.util.Collection;

import rnd.bean.ApplicationDynaBean;
import rnd.mywt.client.MyWTHelper;
import rnd.mywt.client.arb.ARBServiceResponseHandler;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.util.ARUtils;

public class DefaultApplicationHelper extends AbstractApplicationHelper implements ApplicationHelper {

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

							MetadataFormHelper formHelper = new MetadataFormHelper( //
									(String) module.getValue("name"), // Module
									(String) appBean.getValue("name"), // AppBean
									(String) form.getValue("name"), // Form
									(String) appBean.getValue("name"),// View
									(String) appBean.getValue("className")); // ClassName
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
