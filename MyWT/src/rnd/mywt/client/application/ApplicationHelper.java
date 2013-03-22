package rnd.mywt.client.application;

import java.util.Collection;

public interface ApplicationHelper {
	
	void initialiseApplication();

	void addModuleHelper(ModuleHelper moduleHelper);

	ModuleHelper getModuleHelper(String moduleName);

	Collection<ModuleHelper> getModuleHelpers();

}
