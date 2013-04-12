package rnd.mywt.client.application;

import java.util.Collection;

import rnd.bean.ApplicationBean;
import rnd.bean.ApplicationDynaBean;
import rnd.mywt.client.MyWTHelper;
import rnd.mywt.client.arb.ARBServiceResponseHandler;
import rnd.mywt.client.mvc.field.data.text.Label;
import rnd.mywt.client.mvc.page.form.Form;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.util.ARUtils;

public class MetadataFormHelper extends AbstractFormHelper {

	private String className;

	public MetadataFormHelper(String appBeanName, String formName, String viewName, String className) {
		super(appBeanName, formName, viewName);
		this.className = className;
	}

	@Override
	public Form createForm() {

		final Form form = super.createForm();

		Label label = MyWTHelper.getMVCFactory().createLabel(getFormName());
		form.addField(label);

		ApplicationRequest loadReq = ARUtils.createLoadRequest("AD", "Form", getFormName());

		MyWTHelper.getARB().executeRequest(loadReq, new ARBServiceResponseHandler<ApplicationDynaBean>() {

			public void processResult(ApplicationDynaBean formMetadata) {

				Collection<ApplicationDynaBean> fields = (Collection<ApplicationDynaBean>) formMetadata.getListValueReadOnly("field");
				for (ApplicationDynaBean field : fields) {

					form.addField(createTextField((String) field.getValue("label"), (String) field.getValue("boundTo")));
				}
			}
		});
		return form;
	}

	@Override
	public ApplicationBean createApplicationBean() {
		return new ApplicationDynaBean(className);
	}
}
