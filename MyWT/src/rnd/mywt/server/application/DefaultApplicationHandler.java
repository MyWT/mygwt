package rnd.mywt.server.application;

public class DefaultApplicationHandler extends AbstractApplicationHandler {

	DefaultApplicationHandler() {
	}

	static class DefaultApplicationHandlerHolder {
		final static ApplicationHandler sharedInstance = new DefaultApplicationHandler();
	}

	public static ApplicationHandler getSharedInstance() {
		return DefaultApplicationHandlerHolder.sharedInstance;
	}

	public void initialiseApplication() {
	}

	@Override
	public ModuleHandler getModuleHandler(String moduleName) {
		return new DefaultModuleHandler(moduleName);
	}
}
