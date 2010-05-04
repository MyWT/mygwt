package rnd.mywt.client.mvc.field;

import rnd.mywt.client.mvc.MVCBean;

public interface Field extends MVCBean {
	
	String getFieldProperty();

	public interface FieldModel extends Model {
	}

	public interface FieldView extends View {
	}
}
