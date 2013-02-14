package rnd.mywt.client.application;

public class DefaultModuleHelper extends AbstractModuleHelper {

	String moduleName;

	public DefaultModuleHelper(String moduleName) {
		this.moduleName = moduleName;
	}

	@Override
	public String getModuleName() {
		return moduleName;
	}

	@Override
	public void initialiseModule() {
	}

}
