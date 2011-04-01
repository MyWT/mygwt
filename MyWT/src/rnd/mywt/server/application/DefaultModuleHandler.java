package rnd.mywt.server.application;

public class DefaultModuleHandler extends AbstractModuleHandler {

	private static class DefaultModuleHandlerHolder {
		private static DefaultModuleHandler sharedInstace = new DefaultModuleHandler();
	}

	public static ModuleHandler getSharedInstance() {
		return DefaultModuleHandlerHolder.sharedInstace;
	}

	@Override
	public ApplicationBeanHandler getApplicationBeanHandler(String appBeanName) {
		return DefaultApplicationBeanHandler.getSharedInstance();
	}

	@Override
	public void initModule() {
		// Default Module
	}

}
