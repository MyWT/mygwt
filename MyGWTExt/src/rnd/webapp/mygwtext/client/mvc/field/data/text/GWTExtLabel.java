package rnd.webapp.mygwtext.client.mvc.field.data.text;

import rnd.mywt.client.mvc.AbstractMVCBean;
import rnd.mywt.client.mvc.field.data.text.Label;

public class GWTExtLabel extends AbstractMVCBean implements Label {

	protected Label label;

	class Label extends com.gwtext.client.widgets.form.Label {
	}

	public GWTExtLabel(String text) {

		setModel(new GWTExtLabelModel(text));
		setView(new GWTExtLabelView());
	}

	public String getText() {
		return getTextModel().getText();
	}

	public String setText(String text) {
		return getTextModel().setText(text);
	}
	
	public String getFieldProperty() {
		return TEXT;
	}

	Label getLabel() {
		if (this.label == null) {
			this.label = createLabel();
		}
		return this.label;
	}

	protected Label createLabel() {
		return new Label();
	}

	protected TextModel getTextModel() {
		return (TextModel) getModel();
	}

	// Model

	public class GWTExtLabelModel implements LabelModel {

		public GWTExtLabelModel(String text) {
			setText(text);
		}

		public String getText() {
			return getLabel().getText();
		}

		public String setText(String text) {
			String oldText = getText();
			getLabel().setText(text);
			return oldText;
		}
	}

	// View

	public class GWTExtLabelView implements LabelView {

		public Object getViewObject() {
			return getLabel();
		}

	}

}