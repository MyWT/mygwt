package rnd.mywt.client.mvc.field.data.text;

public interface TextField extends Text {

	String LABEL = "label";

	String setLabel(String label);

	String getLabel();

	public interface TextFieldModel extends TextModel {
	}

	public interface TextFieldView extends TextView {
	}

}
