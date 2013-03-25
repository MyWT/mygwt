package rnd.mywt.client.application;

import rnd.mywt.client.MyWTHelper;
import rnd.mywt.client.bean.ApplicationBean;
import rnd.mywt.client.bean.ApplicationDynaBean;
import rnd.mywt.client.expression.RowColumnExpression;
import rnd.mywt.client.mvc.field.Table;
import rnd.mywt.client.mvc.field.data.ReferenceField;
import rnd.mywt.client.mvc.field.data.ReferenceField.ReferenceFieldView;
import rnd.mywt.client.mvc.field.data.text.TextArea;
import rnd.mywt.client.mvc.field.data.text.TextField;
import rnd.mywt.client.mvc.page.board.DataBoard;
import rnd.mywt.client.mvc.page.form.Form;

public abstract class AbstractFormHelper implements FormHelper {

	private final String appBeanName;
	private final String formName;
	private final String viewName;

	public AbstractFormHelper(String appBeanName, String formName, String viewName) {
		this.appBeanName = appBeanName;
		this.formName = formName;
		this.viewName = viewName;
	}

	private Form form;

	public Form getForm() {
		if (this.form == null) {
			this.form = createForm();
		}
		return this.form;
	}

	public Form createForm() {
		Form newForm = MyWTHelper.getMVCFactory().createForm();
		return newForm;
	}

	@Override
	public DataBoard createDataBoard() {
		return null;
	}

	protected DataBoard createDataBoard(String moduleName, String applicationBeanName, String viewName) {
		DataBoard newDataBoard = MyWTHelper.getMVCFactory().createDataBoard(moduleName, applicationBeanName, viewName);
		return newDataBoard;
	}

	protected TextField createTextField(String label, String boundTo) {
		TextField textField = MyWTHelper.getMVCFactory().createTextField(label);
		textField.setBoundTo(boundTo);
		return textField;
	}

	protected TextArea createTextArea(String label, String boundTo) {
		TextArea textArea = MyWTHelper.getMVCFactory().createTextArea(label);
		textArea.setBoundTo(boundTo);
		return textArea;
	}

	protected ReferenceField createReferenceField(String label, String moduleName, String applicationBeanName, String viewName) {
		return MyWTHelper.getMVCFactory().createReferenceField(label, moduleName, applicationBeanName, viewName);
	}

	protected ReferenceField createReferenceField(String label, String moduleName, String applicationBeanName, String viewName, String columnName) {
		ReferenceField referenceField = createReferenceField(label, moduleName, applicationBeanName, viewName);
		((ReferenceFieldView) referenceField.getView()).setDisplayExpresion(new RowColumnExpression(columnName));
		return referenceField;
	}

	protected ReferenceField createReferenceField(String label, String boundTo, String moduleName, String applicationBeanName, String viewName, String columnName) {
		ReferenceField referenceField = createReferenceField(label, moduleName, applicationBeanName, viewName);
		referenceField.setBoundTo(boundTo);
		((ReferenceFieldView) referenceField.getView()).setDisplayExpresion(new RowColumnExpression(columnName));
		return referenceField;
	}

	protected Table createTable() {
		return MyWTHelper.getMVCFactory().createTable(Table.BEAN_BASED);
	}

	@Override
	public ApplicationBean createApplicationBean() {
		return new ApplicationDynaBean();
	}

	@Override
	public String getAppBeanName() {
		return appBeanName;
	}

	@Override
	public String getFormName() {
		return formName;
	}

	@Override
	public String getViewName() {
		return viewName;
	}

}
