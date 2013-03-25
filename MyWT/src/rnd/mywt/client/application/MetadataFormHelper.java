package rnd.mywt.client.application;

import java.util.Collection;

import rnd.mywt.client.MyWTHelper;
import rnd.mywt.client.arb.ARBAsyncCallback;
import rnd.mywt.client.bean.ApplicationDynaBean;
import rnd.mywt.client.mvc.field.data.text.Label;
import rnd.mywt.client.mvc.field.data.text.TextField;
import rnd.mywt.client.mvc.page.form.Form;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.util.ARUtils;

public class MetadataFormHelper extends AbstractFormHelper implements FormHelper {

	private ApplicationDynaBean formMetadata;

	public MetadataFormHelper(String appBeanName, String formName, String viewName, ApplicationDynaBean formMetadata) {
		super(appBeanName, formName, viewName);
		this.formMetadata = formMetadata;
	}

	@Override
	public Form createForm() {

		final Form form = super.createForm();

		Label label = MyWTHelper.getMVCFactory().createLabel(getFormName());
		form.addField(label);

		ApplicationRequest loadReq = ARUtils.createLoadRequest("AD", "Form", getFormName());

		MyWTHelper.getARB().executeRequest(loadReq, new ARBAsyncCallback<ApplicationDynaBean>() {

			@Override
			public void processResult(ApplicationDynaBean app) {

				Collection<ApplicationDynaBean> fields = (Collection<ApplicationDynaBean>) formMetadata.getListValueReadOnly("field");
				for (ApplicationDynaBean field : fields) {

					TextField name_TF = MyWTHelper.getMVCFactory().createTextField((String) field.getValue("lable"));
					name_TF.setBoundTo((String) field.getValue("boundTo"));
					form.addField(name_TF);
				}
			}
		});
		return form;
	}
}
