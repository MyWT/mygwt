package rnd.mywt.server.application;

import rnd.mywt.server.data.ViewMetaData;
import rnd.mywt.server.data.impl.SQLViewMetaDataImpl;

public class DefaultApplicationBeanHandler extends AbstractABHandler {

	DefaultApplicationBeanHandler() {
	}

	private static class DefaultApplicationBeanHandlerHolder {
		static DefaultApplicationBeanHandler sharedInstance = new DefaultApplicationBeanHandler();
	}

	public static ApplicationBeanHandler getSharedInstance() {
		return DefaultApplicationBeanHandlerHolder.sharedInstance;
	}

	public ViewMetaData getViewMetaData(String viewName) {

		SQLViewMetaDataImpl vmd = new SQLViewMetaDataImpl();
		vmd.setViewQuery("Select * From " + viewName);
		vmd.setViewName(viewName);

		return vmd;
	}

}
