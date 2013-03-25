package rnd.mywt.client.mvc.page.board;

import rnd.mywt.client.mvc.page.Page;

public interface Board extends Page {

	public enum BoardType {
		FORM_BOARD, DATA_BOARD;
	}

	BoardType getBoardType();

	String MODULE_NAME = "moduleName";

	String getModuleName();

	String APPLICATION_BEAN_NAME = "appBeanName";

	String getApplicationBeanName();

}
