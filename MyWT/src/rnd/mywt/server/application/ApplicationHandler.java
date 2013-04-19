package rnd.mywt.server.application;

public interface ApplicationHandler {

	String getApplicationName();

	void registerModule(String moduleName, ModuleHandler moduleHandler);

	ModuleHandler getModuleHandler(String moduleName);

}
