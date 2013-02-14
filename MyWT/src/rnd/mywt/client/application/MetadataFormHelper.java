package rnd.mywt.client.application;

import rnd.mywt.client.bean.ApplicationBean;
import rnd.mywt.client.bean.ApplicationDynaBean;

public class MetadataFormHelper extends AbstractFormHelper implements FormHelper {

	private String formName;
	private String viewName;

	public MetadataFormHelper(String formName, String viewName) {
		this.formName = formName;
		this.viewName = viewName;
	}

	@Override
	public String getFormName() {
		return formName;
	}

	@Override
	public String getViewName() {
		return viewName;
	}

	@Override
	public ApplicationBean createApplicationBean() {
		return new ApplicationDynaBean();
	}

}
