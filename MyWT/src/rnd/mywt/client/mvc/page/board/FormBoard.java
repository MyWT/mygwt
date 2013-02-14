package rnd.mywt.client.mvc.page.board;

import rnd.mywt.client.mvc.page.form.Form;

public interface FormBoard extends Board {

	ActionBase getActionBase();

	Form getForm();

	void setDataBoard(DataBoard dataBoard);

	DataBoard getDataBoard();

	String APPLICATION_BEAN_ID = "applicationBeanId";

	void setApplicationBeanId(Long appBeanId);

	Long getApplicationBeanId();

	public interface FormBoardView extends View {
	}

}
