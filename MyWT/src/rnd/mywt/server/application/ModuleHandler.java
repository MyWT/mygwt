package rnd.mywt.server.application;

import rnd.mywt.client.bean.ApplicationBean;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationResponse;
import rnd.op.ObjectLifeCycleSupport;
import rnd.op.ObjectPersistor;

public interface ModuleHandler extends ObjectLifeCycleSupport<ApplicationBean> {

	// Callback

	void initModule();

	// Intialization

	void registerApplicationBean(String appBeanName);

	void registerApplicationBean(String appBeanName, Class appBeanType);

	void registerApplicationBean(String appBeanName, Class appBeanType, ApplicationBeanHandler applicationBeanHandler);

	// Helper

	ApplicationBeanHandler getApplicationBeanHandler(String appBeanName);

	Class getApplicationBeanType(String appBeanName);

	// Main

	void handleRequest(ApplicationRequest req, ApplicationResponse resp);
	
	ObjectPersistor getObjectPersistor();

	String getModuleName();

}
