package rnd.mywt.client.mvc.field.data.text;

import java.util.Date;

public interface DateField extends TextField {

	public interface NumberFieldModel extends TextFieldModel {

		void setDate(Date value);

		Date getDate();
	}

	public interface NumberFieldView extends TextFieldView {
	}

}
