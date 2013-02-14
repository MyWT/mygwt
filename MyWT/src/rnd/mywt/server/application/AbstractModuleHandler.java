package rnd.mywt.server.application;

import rnd.mywt.client.bean.ApplicationBean;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationResponse;
import rnd.op.ObjectPersistor;
import rnd.op.jpersis.JPObjectPersistor;

public abstract class AbstractModuleHandler implements ModuleHandler {

	private String moduleName;

	private ObjectPersistor op;
	private final ModuleHandlerDelegate delegate = new ModuleHandlerDelegate(this);

	public AbstractModuleHandler() {
		op = new JPObjectPersistor();
		delegate.initModule();
	}

	@Override
	public void handleRequest(ApplicationRequest req, ApplicationResponse resp) {
		delegate.handleRequest(req, resp);
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
	public void registerApplicationBean(String appBeanName) {
		delegate.registerApplicationBean(appBeanName);
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
	public ObjectPersistor getObjectPersistor() {
		return op;
	}

	@Override
	public void deleteObject(Object id, Class objType) {
		delegate.deleteObject(id, objType);
	}

	@Override
	public ApplicationBean findObject(Object id, Class<ApplicationBean> objType) {
		return delegate.findObject(id, objType);
	}

	@Override
	public ApplicationBean saveObject(ApplicationBean object) {
		return delegate.saveObject(object);
	}

	public ApplicationBean updateObject(Object id, ApplicationBean object) {
		return delegate.saveObject(object);
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleName() {
		return moduleName;
	}

}
