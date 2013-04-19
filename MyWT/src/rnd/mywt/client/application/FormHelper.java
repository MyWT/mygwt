package rnd.mywt.client.application;

import rnd.bean.ApplicationBean;
import rnd.mywt.client.mvc.page.board.DataBoard;
import rnd.mywt.client.mvc.page.form.Form;

public interface FormHelper extends FormHelperCallback {

	String getAppBeanName();

	String getFormName();

	String getViewName();

	Form createForm();

	ApplicationBean createApplicationBean();

	DataBoard createDataBoard();

	boolean shouldAutoBind();

}
