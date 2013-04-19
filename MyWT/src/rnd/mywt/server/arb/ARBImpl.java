package rnd.mywt.server.arb;

import rnd.mywt.client.arb.ARB;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationResponse;
import rnd.mywt.server.application.ApplicationHandler;
import rnd.mywt.server.application.ApplicationHandlerPool;
import rnd.mywt.server.application.DefaultModuleHandler;
import rnd.mywt.server.application.ModuleHandler;

public class ARBImpl implements ARB {

	public void initialiseApplication() {
		try {
			ApplicationHandler appHandler = (ApplicationHandler) Class.forName("rnd.webapp.mywtapp.server.MyApplicationHandler").newInstance();
			ApplicationHandlerPool.registerApplicationHandler(appHandler.getApplicationName(), appHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ApplicationResponse executeRequest(ApplicationRequest req) {

		ApplicationResponse resp = new ApplicationResponse();
		try {
			String module = req.getModule();

			ModuleHandler moduleHandler = ApplicationHandlerPool.getApplicationHandler("myapp").getModuleHandler(module);
			if (moduleHandler == null) {
				moduleHandler = DefaultModuleHandler.getInstance(module);
			}

			moduleHandler.handleRequest(req, resp);
		} catch (Throwable e) {
			e.printStackTrace();
			resp.setThrowable(e);
		}
		return resp;
	}
}
