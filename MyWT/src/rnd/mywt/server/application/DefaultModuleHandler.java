package rnd.mywt.server.application;

public class DefaultModuleHandler extends AbstractModuleHandler {

	public DefaultModuleHandler(String moduleName) {
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

	public static ModuleHandler getInstance(String moduleName) {
		return new DefaultModuleHandler(moduleName);
	}

}
