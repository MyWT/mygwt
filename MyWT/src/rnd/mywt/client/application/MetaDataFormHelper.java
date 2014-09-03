package rnd.mywt.client.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rnd.bean.ApplicationBean;
import rnd.bean.ApplicationDynaBean;
import rnd.expression.Expression;
import rnd.mywt.client.MyWTHelper;
import rnd.mywt.client.arb.ARBServiceResponseHandler;
import rnd.mywt.client.data.impl.FilterInfoImpl;
import rnd.mywt.client.mvc.field.Field;
import rnd.mywt.client.mvc.page.board.DataBoard;
import rnd.mywt.client.mvc.page.form.Form;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.util.ARUtils;
import rnd.utils.WrapperUtils;

public class MetaDataFormHelper extends AbstractFormHelper {

	private String className;
	private ApplicationDynaBean formMetadata;

	public MetaDataFormHelper(String moduleName, String appBeanName, String formName, String viewName, String className) {
		super(moduleName, appBeanName, formName, viewName);
		this.className = className;
	}

	public ApplicationDynaBean getFormMetadata() {
		return formMetadata;
	}

	public void setFormMetaData(ApplicationDynaBean formMetadata) {
		this.formMetadata = formMetadata;
	}

	@Override
	public Form createForm() {
		Form form = super.createForm();
		form.addField(MyWTHelper.getMVCFactory().createLabel(getFormName()));
		initForm(form);
		return form;
	}

	public void initForm(final Form form) {

		Collection<ApplicationDynaBean> fields = (Collection<ApplicationDynaBean>) formMetadata.getListValueReadOnly("field");
		for (ApplicationDynaBean fieldMD : fields) {
			form.addField(createField(fieldMD));
		}
	}

	private Field createField(ApplicationDynaBean fieldMD) {
		Field field;
		if (WrapperUtils.getBoolean(fieldMD.getValue("reference"))) {
			field = createReferenceField(//
			(String) fieldMD.getValue("label"), // Label
			(String) fieldMD.getValue("refModuleName"), // Ref Module
			(String) fieldMD.getValue("refApplicationBeanName"), // Ref App Bean 
			(String) fieldMD.getValue("refViewName"), // Ref View
			(String) fieldMD.getValue("refColumnName")); // Ref Column
		} else {
			field = createTextField((String) fieldMD.getValue("label"), (String) fieldMD.getValue("boundTo"));
		}
		return field;
	}

	@Override
	public DataBoard createDataBoard() {

		final DataBoard dataBoard = createDataBoard(getModuleName(), getAppBeanName(), getViewName());

		ApplicationRequest loadReq = ARUtils.createLoadRequest("AD", "Form", getFormName());

		MyWTHelper.getARB().executeRequest(loadReq, new ARBServiceResponseHandler<ApplicationDynaBean>() {

			public void processResult(ApplicationDynaBean formMD) {

				setFormMetaData(formMD);

				FilterInfoImpl filterInfo = new FilterInfoImpl("default");

				Collection<ApplicationDynaBean> fields = (Collection<ApplicationDynaBean>) formMD.getListValueReadOnly("field");
				for (ApplicationDynaBean fieldMD : fields) {

					List<Expression> filterParamExps = new ArrayList<Expression>();
					List filterParamExpObjs = new ArrayList();

					if (WrapperUtils.getBoolean(fieldMD.getValue("context"))) {
						filterParamExps.add(dataBoard.addContextField((String) fieldMD.getValue("name"), createField(fieldMD)));
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