package rnd.webapp.mygwt.server;

import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationResponse;
import rnd.mywt.server.application.ApplicationHandler;
import rnd.mywt.server.application.DefaultModuleHandler;
import rnd.mywt.server.application.ModuleHandler;
import rnd.webapp.mygwt.client.ARB;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ARBImpl extends RemoteServiceServlet implements ARB {

	private ApplicationHandler appHandler = null;

	@Override
	public void init() {
//		initialiseApplication();
	}

//	private void initialiseApplication() {
//		// if (Debugger.D.pushCheck("rnd.webapp.mwt.server.ARBImpl.initialiseApplication")) {
//		// Debugger.D.push(this, null);
//		// }
//		// try {
//		try {
//			this.appHandler = (ApplicationHandler) Class.forName(MyApplicationHandler.class.getName()).newInstance();
//		} catch (Exception e) {
//			e.printStackTrace();
//			// throw new RuntimeException(e);
//		}
//		// D.println("appHandler", this.appHandler);
//
//		if (this.appHandler == null) {
//			this.appHandler = DefaultApplicationHandler.getSharedInstance();
//		}
//
//		// }
//		// finally {
//		// Debugger.D.pop("rnd.webapp.mwt.server.ARBImpl.initialiseApplication");
//		// }
//	}

	public ApplicationResponse executeRequest(ApplicationRequest req) {
		// if (Debugger.D.pushCheck("rnd.webapp.mwt.server.ARBImpl.executeRequest")) {
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
				moduleHandler = DefaultModuleHandler.getSharedInstance();
			}
			// D.println("moduleHandler", moduleHandler);
			moduleHandler.executeRequest(req, resp);
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
