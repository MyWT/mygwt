package rnd.mywt.client.mvc.page.form;

import java.util.List;

import rnd.mywt.client.bean.ApplicationBean;
import rnd.mywt.client.mvc.field.Field;
import rnd.mywt.client.mvc.page.Page;

public interface Form extends Page {

	String APPLICATION_BEAN = "applicationBean";

	void addField(Field field);

	void removeField(Field field);

	Field getField(int index);

	List<Field> getFields();

	public interface FormModel extends Model {

		void setApplicationBean(ApplicationBean applicationBean);

		ApplicationBean getApplicationBean();
	}

}
