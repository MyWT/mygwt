package rnd.mywt.client.application;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import rnd.bean.ApplicationDynaBean;
import rnd.mywt.client.MyWTHelper;
import rnd.mywt.client.application.ApplicationHelper;
import rnd.mywt.client.application.ModuleHelper;
import rnd.mywt.client.arb.ARBServiceResponseHandler;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.util.ARUtils;

public abstract class AbstractApplicationHelper implements ApplicationHelper {

	protected String applicationName;
	protected Map moduleHelperMap = new HashMap();

	public AbstractApplicationHelper(String applicationName) {
		setApplicationName(applicationName);
	}

	public void addModuleHelper(ModuleHelper moduleHelper) {
		this.moduleHelperMap.put(moduleHelper.getModuleName(), moduleHelper);
	}

	public ModuleHelper getModuleHelper(String moduleName) {
		return (ModuleHelper) this.moduleHelperMap.get(moduleName);
	}

	public Collection getModuleHelpers() {
		return this.moduleHelperMap.values();
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	@Override
	public void initializeApplication() {

		ApplicationRequest loadReq = ARUtils.createLoadRequest("AD", "Application", applicationName);

		MyWTHelper.getARB().executeRequest(loadReq, new ARBServiceResponseHandler<ApplicationDynaBean>() {

			@Override
			public void processResult(ApplicationDynaBean app) {

				Collection<ApplicationDynaBean> modules = (Collection<ApplicationDynaBean>) app.getListValueReadOnly("module");
				for (ApplicationDynaBean module : modules) {

					String moduleName = (String) module.getValue("name");

					ModuleHelper moduleHelper = getModuleHelper(moduleName);

					if (moduleHelper == null) {
						moduleHelper = new DefaultModuleHelper(moduleName);
						addModuleHelper(moduleHelper);
					}

					Collection<ApplicationDynaBean> appBeans = (Collection<ApplicationDynaBean>) module.getListValueReadOnly("applicationBean");
					for (ApplicationDynaBean appBean : appBeans) {

						Collection<ApplicationDynaBean> forms = (Collection<ApplicationDynaBean>) appBean.getListValueReadOnly("form");
						for (ApplicationDynaBean form : forms) {

							MetaDataFormHelper formHelper = new MetaDataFormHelper( //
									(String) module.getValue("name"), // Module
									(String) appBean.getValue("name"), // AppBean
									(String) form.getValue("name"), // Form
									(String) appBean.getValue("name"),// View
									appBean.getClassName()); // ClassName
							
							moduleHelper.addFormHelper(formHelper);
						}
					}
				}

				MyWTHelper.getHomePage().initializeFormAction();
			}
		});
	}

}
