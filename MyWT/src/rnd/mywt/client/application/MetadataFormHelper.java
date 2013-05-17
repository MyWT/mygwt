package rnd.mywt.client.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rnd.bean.ApplicationBean;
import rnd.bean.ApplicationDynaBean;
import rnd.expression.Expression;
import rnd.mywt.client.Logger;
import rnd.mywt.client.MyWTHelper;
import rnd.mywt.client.arb.ARBServiceResponseHandler;
import rnd.mywt.client.data.impl.FilterInfoImpl;
import rnd.mywt.client.mvc.field.data.ReferenceField;
import rnd.mywt.client.mvc.field.data.text.Label;
import rnd.mywt.client.mvc.page.board.DataBoard;
import rnd.mywt.client.mvc.page.form.Form;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.util.ARUtils;
import rnd.utils.WrapperUtils;

public class MetadataFormHelper extends AbstractFormHelper {

	private String className;
	private ApplicationDynaBean formMetadata;

	public MetadataFormHelper(String moduleName, String appBeanName, String formName, String viewName, String className) {
		super(moduleName, appBeanName, formName, viewName);
		this.className = className;
	}

	public ApplicationDynaBean getFormMetadata() {
		return formMetadata;
	}

	public void setFormMetadata(ApplicationDynaBean formMetadata) {
		this.formMetadata = formMetadata;
	}

	@Override
	public Form createForm() {

		final Form form = super.createForm();

		Label label = MyWTHelper.getMVCFactory().createLabel(getFormName());
		form.addField(label);

		initForm(form);

		return form;
	}

	public void initForm(final Form form) {

		// ApplicationRequest loadReq = ARUtils.createLoadRequest("AD", "Form",
		// getFormName());
		//
		// MyWTHelper.getARB().executeRequest(loadReq, new
		// ARBServiceResponseHandler<ApplicationDynaBean>() {
		//
		// public void processResult(ApplicationDynaBean formMetadata) {

		if (formMetadata == null) {
			Logger.log("Metadata not Intialized");
			return;
		}

		Collection<ApplicationDynaBean> fields = (Collection<ApplicationDynaBean>) formMetadata.getListValueReadOnly("field");
		for (ApplicationDynaBean field : fields) {
			// TODO add reference attribute to FMD
			if (WrapperUtils.getBoolean(field.getValue("reference"))) {
				// TODO autocreate RField
			} else {
				form.addField(createTextField((String) field.getValue("label"), (String) field.getValue("boundTo")));
			}
		}

		// ApplicationBean appBean = ((FormModel)
		// form.getModel()).getApplicationBean();
		// // after creating Fields
		// Long appBeanId = appBean.getId();
		// // Logger.log("appBeanId", appBeanId);
		//
		// // Init Update Form
		// if (appBeanId != null) {
		// BindingManager.initForm(form, (_BoundBean) appBean);
		// }
		// // Bind Form after creating Fields
		// BindingManager.bindForm(form, (_BoundBean) appBean);
		// // }
		// });
	}

	@Override
	public DataBoard createDataBoard() {

		final DataBoard dataBoard = createDataBoard(getModuleName(), getAppBeanName(), getViewName());

		ApplicationRequest loadReq = ARUtils.createLoadRequest("AD", "Form", getFormName());

		MyWTHelper.getARB().executeRequest(loadReq, new ARBServiceResponseHandler<ApplicationDynaBean>() {

			public void processResult(ApplicationDynaBean formMetadata) {

				setFormMetadata(formMetadata);

				FilterInfoImpl filterInfo = new FilterInfoImpl("default");

				Collection<ApplicationDynaBean> fields = (Collection<ApplicationDynaBean>) formMetadata.getListValueReadOnly("field");
				for (ApplicationDynaBean field : fields) {

					List<Expression> filterParamExps = new ArrayList<Expression>();
					List filterParamExpObjs = new ArrayList();
					// TODO add context attribute to FMD
					if (WrapperUtils.getBoolean(field.getValue("context"))) {

						ReferenceField applicationId_RF = createReferenceField("Application", "AD", "Application", "Application", "Name");
						Expression applicationIdCtxExp = dataBoard.addContextField("applicationId", applicationId_RF);
						filterParamExps.add(applicationIdCtxExp);
						filterParamExpObjs.add(dataBoard.getModel());
					}

					filterInfo.setFilterParamExpressionObjects(filterParamExpObjs.toArray());
					filterInfo.setFilterParamExpressions(filterParamExps.toArray(new Expression[0]));

				}
				dataBoard.setFilter(filterInfo);

			}
		});

		return dataBoard;
	}

	@Override
	public ApplicationBean createApplicationBean() {
		return new ApplicationDynaBean(className);
	}

	@Override
	public boolean shouldAutoBind() {
		return false;
	}

}
