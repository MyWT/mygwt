package rnd.mywt.client.mvc.field.data.text;

public interface NumberField extends TextField {

	public interface NumberFieldModel extends TextFieldModel {

		void setNumer(Number value);

		Number getNumer();
	}

	public interface NumberFieldView extends TextFieldView {
	}

}
