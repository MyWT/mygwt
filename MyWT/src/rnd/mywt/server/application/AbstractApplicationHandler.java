package rnd.mywt.server.application;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractApplicationHandler implements ApplicationHandler {

	private String applicationName;

	private static Map<String, ModuleHandler> moduleHandlerMap = new HashMap<String, ModuleHandler>();

	public AbstractApplicationHandler(String appName) {
		setApplicationName(appName);
		initialiseApplication();
	}

	protected abstract void initialiseApplication();

	public void registerModule(String moduleName, ModuleHandler moduleHandler) {
		moduleHandlerMap.put(moduleHandler.getModuleName(), moduleHandler);
		moduleHandler.setApplicationHandler(this);
	}

	public ModuleHandler getModuleHandler(String moduleName) {
		return moduleHandlerMap.get(moduleName);
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

}
