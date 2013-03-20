package rnd.mywt.client.application;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import rnd.mywt.client.application.AbstractApplicationHelper;

public abstract class AbstractModuleHelper extends AbstractApplicationHelper implements ModuleHelper {

	private String moduleName;
	protected Map formHelperMap = new HashMap();

	public AbstractModuleHelper(String moduleName) {
		super(moduleName);
		this.moduleName = moduleName;
		initialiseModule();
	}

//	public void addFormHelper(String formName, String viewName) {
//		this.formHelperMap.put(formName, new MetadataFormHelper(formName, viewName));
//	}

	public void addFormHelper(String formName, String viewName, FormHelper formHelper) {
		this.formHelperMap.put(formName, formHelper);
	}

	public void addFormHelper(FormHelper formHelper) {
		this.formHelperMap.put(formHelper.getFormName(), formHelper);
	}

	public void addFormHelper(String formName, FormHelper formHelper) {
		this.formHelperMap.put(formHelper.getFormName(), formHelper);
	}

	public FormHelper getFormHelper(String formName) {
		return (FormHelper) this.formHelperMap.get(formName);
	}

	public Collection getFormHelpers() {
		return this.formHelperMap.values();
	}

	public abstract void initialiseModule();

	public String getModuleName() {
		return moduleName;
	}

	@Override
	public void initialiseApplication() {
	}

}
