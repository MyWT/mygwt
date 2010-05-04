package rnd.mywt.client.mvc.field.data.text;

import rnd.mywt.client.mvc.field.data.DataField;

public interface Text extends DataField {

	String TEXT = "text";

	String setText(String text);

	String getText();

	public interface TextModel extends DataFieldModel {

		String setText(String text);

		String getText();
	}

	public interface TextView extends DataFieldView {
	}
}
