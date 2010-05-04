package rnd.mywt.client.application;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import rnd.mywt.client.application.ApplicationHelper;
import rnd.mywt.client.application.ModuleHelper;

public abstract class AbstractApplicationHelper implements ApplicationHelper {

	protected Map moduleHelperMap = new HashMap();

	public AbstractApplicationHelper() {
		initialiseApplication();
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

	public abstract void initialiseApplication();

}
