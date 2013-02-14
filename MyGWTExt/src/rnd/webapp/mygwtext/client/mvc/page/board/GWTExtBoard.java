package rnd.webapp.mygwtext.client.mvc.page.board;

import rnd.mywt.client.mvc.AbstractMVCBean;
import rnd.mywt.client.mvc.page.board.Board;

public abstract class GWTExtBoard extends AbstractMVCBean implements Board {

	public GWTExtBoard(String moduleName, String appBeanName) {
		setValue(MODULE_NAME, moduleName);
		setValue(APPLICATION_BEAN_NAME, appBeanName);
	}

	public String getModuleName() {
		return (String) getValue(MODULE_NAME);
	}

	public String getApplicationBeanName() {
		return (String) getValue(APPLICATION_BEAN_NAME);
	}

}
