package rnd.mywt.server.application;

import rnd.bean.ApplicationBean;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationResponse;
import rnd.op.ObjectLifeCycleSupport;
import rnd.op.ObjectPersistor;

public interface ModuleHandler extends ObjectLifeCycleSupport<ApplicationBean> {

	// Callback

	void initModule();

	// Intialization

	// void registerApplicationBean(String appBeanName);

	void registerApplicationBean(String appBeanName, Class<? extends ApplicationBean> appBeanType);

	void registerApplicationBean(String appBeanName, Class<? extends ApplicationBean> appBeanType, ApplicationBeanHandler applicationBeanHandler);

	ApplicationHandler getApplicationHandler();

	void setApplicationHandler(ApplicationHandler abstractApplicationHandler);

	// Helper

	ApplicationBeanHandler getApplicationBeanHandler(String appBeanName);

	Class getApplicationBeanType(String appBeanName);

	// Main

	void handleRequest(ApplicationRequest req, ApplicationResponse resp);

	ObjectPersistor getObjectPersistor();

	String getModuleName();

}
