package rnd.mywt.client.application;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import rnd.mywt.client.application.AbstractApplicationHelper;

public abstract class AbstractModuleHelper extends AbstractApplicationHelper implements ModuleHelper {

	protected Map formHelperMap = new HashMap();

	public AbstractModuleHelper() {
		initialiseModule();
	}

	public void addFormHelper(FormHelper formHelper) {
		this.formHelperMap.put(formHelper.getFormName(), formHelper);
	}

	public FormHelper getFormHelper(String formName) {
		return (FormHelper) this.formHelperMap.get(formName);
	}

	public Collection getFormHelpers() {
		return this.formHelperMap.values();
	}

	public abstract void initialiseModule();

	@Override
	public void initialiseApplication() {
	}

}
