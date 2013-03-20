package rnd.mywt.client.application;

import rnd.mywt.client.bean.ApplicationDynaBean;
import rnd.mywt.client.mvc.page.form.Form;

public class MetadataFormHelper extends AbstractFormHelper implements FormHelper {

	private ApplicationDynaBean formMetadata;

	public MetadataFormHelper(String formName, String viewName, ApplicationDynaBean formMetadata) {
		super(formName, viewName);
		this.formMetadata = formMetadata;
	}

	@Override
	public Form createForm() {
		return buildForm(super.createForm(), formMetadata);

	}

	private static Form buildForm(Form form, ApplicationDynaBean formMetadata) {
		return form;
	}


}
