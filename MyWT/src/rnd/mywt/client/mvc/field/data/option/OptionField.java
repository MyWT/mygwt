package rnd.mywt.client.mvc.field.data.option;

import rnd.mywt.client.mvc.field.data.DataField;

public interface OptionField extends DataField {

	public interface OptionFieldModel extends DataFieldModel {

		void setBoolean(Boolean value);

		Boolean getBoolean();
	}

	public interface OptionFieldView extends DataFieldView {
	}
}
