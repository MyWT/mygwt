package rnd.mywt.server.application;

import rnd.mywt.client.bean.ApplicationBean;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationResponse;
import rnd.op.ObjectLifeCycle;

public interface ModuleHandler extends ObjectLifeCycle<ApplicationBean> {

	// Callback

	void initModule();

	// Intialization

	void registerApplicationBean(String appBeanName, Class appBeanType);

	void registerApplicationBean(String appBeanName, Class appBeanType, ApplicationBeanHandler applicationBeanHandler);

	// Helper

	ApplicationBeanHandler getApplicationBeanHandler(String appBeanName);

	Class getApplicationBeanType(String appBeanName);

	// Main

	void executeRequest(ApplicationRequest req, ApplicationResponse resp);

}
