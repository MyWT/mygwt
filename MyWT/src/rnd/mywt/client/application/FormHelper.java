package rnd.mywt.client.application;

import rnd.mywt.client.bean.ApplicationBean;
import rnd.mywt.client.mvc.page.form.Form;

public interface FormHelper {

	String getFormName();

	String getViewName();

	Form createForm();

	ApplicationBean createApplicationBean();

}
