package rnd.mywt.server.application;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractApplicationHandler implements ApplicationHandler {

	private static Map<String, ModuleHandler> moduleHandlerMap = new HashMap<String, ModuleHandler>();

	public AbstractApplicationHandler() {
		initialiseApplication();
	}

	protected abstract void initialiseApplication();

	public void registerModule(String moduleName, ModuleHandler moduleHandler) {
		moduleHandlerMap.put(moduleHandler.getModuleName(), moduleHandler);
	}

	public ModuleHandler getModuleHandler(String moduleName) {
		return moduleHandlerMap.get(moduleName);
	}

}
