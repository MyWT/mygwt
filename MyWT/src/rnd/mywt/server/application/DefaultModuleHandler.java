package rnd.mywt.server.application;

public class DefaultModuleHandler extends AbstractModuleHandler {

	private DefaultModuleHandler(String moduleName) {
		super(moduleName);
	}

	@Override
	public ApplicationBeanHandler getApplicationBeanHandler(String appBeanName) {
		return DefaultApplicationBeanHandler.getSharedInstance();
	}

	@Override
	public void initModule() {
		// Default Module
	}

	public static ModuleHandler getNewInstance(String moduleName) {
		return new DefaultModuleHandler(moduleName);
	}

}
