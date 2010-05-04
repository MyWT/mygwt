package rnd.mywt.client.application;

import java.util.Collection;

public interface ModuleHelper extends ApplicationHelper {

	String getModuleName();

	void addFormHelper(FormHelper formHelper);

	FormHelper getFormHelper(String formName);

	Collection getFormHelpers();

}
