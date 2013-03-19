package rnd.mywt.server.arb;

import rnd.mywt.client.arb.ARB;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationResponse;
import rnd.mywt.server.application.ApplicationHandler;
import rnd.mywt.server.application.DefaultApplicationHandler;
import rnd.mywt.server.application.DefaultModuleHandler;
import rnd.mywt.server.application.ModuleHandler;

public class ARBImpl implements ARB {

	private ApplicationHandler appHandler = null;

	public void initialiseApplication() {
		try {
			this.appHandler = (ApplicationHandler) Class.forName("rnd.webapp.mywtapp.server.MyApplicationHandler").newInstance();
//			this.appHandler = (ApplicationHandler) Class.forName("rnd.webapp.adapp.server.ADAppHandler").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (this.appHandler == null) {
			this.appHandler = DefaultApplicationHandler.getSharedInstance();
		}

	}

	public ApplicationResponse executeRequest(ApplicationRequest req) {
		// if
		// (Debugger.D.pushCheck("rnd.webapp.mwt.server.ARBImpl.executeRequest"))
		// {
		// Debugger.D.push(this, new Object[] { "req", req });
		// }
		// try {
		ApplicationResponse resp = new ApplicationResponse();
		try {
			String module = req.getModule();
			// D.println("module", module);

			ModuleHandler moduleHandler = this.appHandler.getModuleHandler(module);
			// D.println("moduleHandler", moduleHandler);

			if (moduleHandler == null) {
				moduleHandler = DefaultModuleHandler.getInstance(module);
			}
			// D.println("moduleHandler", moduleHandler);
			moduleHandler.handleRequest(req, resp);
		} catch (Throwable e) {
			e.printStackTrace();
			// throw new RuntimeException(e);
			resp.setThrowable(e);
		}
		return resp;
		// }
		// finally {
		// Debugger.D.pop("rnd.webapp.mwt.server.ARBImpl.executeRequest");
		// }
	}
}
