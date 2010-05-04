package rnd.webapp.mygwtext.client.mvc.field.data.text;

import rnd.mywt.client.mvc.AbstractMVCBean;
import rnd.mywt.client.mvc.field.data.text.TextField;

import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.event.TextFieldListenerAdapter;

public class GWTExtTextField extends AbstractMVCBean implements TextField {

	private com.gwtext.client.widgets.form.TextField textField;

	// public class TextField extends com.gwtext.client.widgets.form.TextField {
	// }

	public GWTExtTextField(String label) {
		setLabel(label);
		setModel(new GWTExtTextFieldModel());
		setView(new GWTExtTextFieldView());
	}

	@Override
	public Object getValue(String propertyName) {
		if (propertyName.equals(TEXT)) { return getText(); }
		return super.getValue(propertyName);
	}

	@Override
	// TODO verify override set Value / or do by listner
	public Object setValue(String propertyName, Object value) {
		if (propertyName.equals(TEXT)) { return setText((String) value); }
		return super.setValue(propertyName, value);
	}

	public String getLabel() {
		return (String) getValue(LABEL);
	}

	public String setLabel(String label) {
		return (String) setValue(LABEL, label);
	}

	public String getText() {
		return getTextFieldModel().getText();
	}

	public String setText(String text) {
		String oldText = getText();
		getTextFieldModel().setText(text);
		fireValueChange(TEXT, oldText, text);
		return oldText;
	}

	public String getFieldProperty() {
		return TEXT;
	}

	protected com.gwtext.client.widgets.form.TextField getTextField() {
		if (this.textField == null) {
			this.textField = createTextField();
		}
		return this.textField;
	}

	protected com.gwtext.client.widgets.form.TextField createTextField() {

		com.gwtext.client.widgets.form.TextField newTextField = getNewTextField();

		newTextField.setLabel(getLabel());
		newTextField.addListener(new TextFieldListenerAdapter() {

			public void onChange(Field field, Object newVal, Object oldVal) {
				// Logger.startMethod("TextFieldListenerAdapter", "onChange");
				// Logger.log("oldVal", oldVal);
				// Logger.log("newVal", newVal);
				fireValueChange(TEXT, oldVal, newVal);
				// Logger.endMethod("TextFieldListenerAdapter", "onChange");
			}
		});

		return newTextField;
	}

	protected com.gwtext.client.widgets.form.TextField getNewTextField() {
		return new com.gwtext.client.widgets.form.TextField();
	}

	protected TextFieldModel getTextFieldModel() {
		return (TextFieldModel) getModel();
	}

	// Model

	public class GWTExtTextFieldModel implements TextFieldModel {

		public String getText() {
			return getTextField().getText();
		}

		public String setText(String text) {
			String oldText = getText();
			getTextField().setValue(text);
			return oldText;
		}
	}

	// View

	public class GWTExtTextFieldView implements TextFieldView {

		public Object getViewObject() {
			return getTextField();
		}

	}

}