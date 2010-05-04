package rnd.mywt.server.application;

import rnd.mywt.client.bean.ApplicationBean;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationResponse;

public class AbstractModuleHandler implements ModuleHandler {

	private ModuleHandlerDelegate delegate = new ModuleHandlerDelegate(this);
	
	public AbstractModuleHandler() {
		delegate.initModule();
	}

	@Override
	public void executeRequest(ApplicationRequest req, ApplicationResponse resp) {
		delegate.executeRequest(req, resp);
	}

	@Override
	public ApplicationBeanHandler getApplicationBeanHandler(String appBeanName) {
		return delegate.getApplicationBeanHandler(appBeanName);
	}

	@Override
	public Class getApplicationBeanType(String appBeanName) {
		return delegate.getApplicationBeanType(appBeanName);
	}

	@Override
	public void registerApplicationBean(String appBeanName, Class appBeanType) {
		delegate.registerApplicationBean(appBeanName, appBeanType);
	}

	@Override
	public void registerApplicationBean(String appBeanName, Class appBeanType, ApplicationBeanHandler applicationBeanHandler) {
		delegate.registerApplicationBean(appBeanName, appBeanType, applicationBeanHandler);
	}

	@Override
	public void initModule() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteObject(Object id, Class objType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ApplicationBean findObject(Object id, Class<ApplicationBean> objType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationBean saveObject(ApplicationBean object) {
		// TODO Auto-generated method stub
		return null;
	}

}
