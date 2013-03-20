package rnd.mywt.client.application;

import java.util.Collection;

import rnd.mywt.client.MyWTHelper;
import rnd.mywt.client.arb.ARBAsyncCallback;
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

		ApplicationRequest loadReq = ARUtils.createLoadRequest("AD", "Application", applicationName);

		MyWTHelper.getARB().executeRequest(loadReq, new ARBAsyncCallback() {

			@Override
			public void onSuccess(ApplicationResponse resp) {
				super.onSuccess(resp);

				ApplicationDynaBean app = (ApplicationDynaBean) resp.getResult();

				Collection<ApplicationDynaBean> modules = (Collection<ApplicationDynaBean>) app.getListValueReadOnly("module");
				for (ApplicationDynaBean module : modules) {

					DefaultModuleHelper moduleHelper = new DefaultModuleHelper((String) module.getValue("name"));

					Collection<ApplicationDynaBean> appBeans = (Collection<ApplicationDynaBean>) module.getListValueReadOnly("applicationBean");
					for (ApplicationDynaBean appBean : appBeans) {

						Collection<ApplicationDynaBean> forms = (Collection<ApplicationDynaBean>) appBean.getListValueReadOnly("form");
						for (ApplicationDynaBean form : forms) {

							MetadataFormHelper formHelper = new MetadataFormHelper((String) form.getValue("name"), (String) form.getValue("viewName"), form);
							moduleHelper.addFormHelper(formHelper);
						}
					}
					addModuleHelper(moduleHelper);
				}

				MyWTHelper.getHomePage().initializeFormAction();

			}

		});

	}
}
