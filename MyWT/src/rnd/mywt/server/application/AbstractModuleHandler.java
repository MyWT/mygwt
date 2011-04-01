package rnd.mywt.server.application;

import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationResponse;
import rnd.op.ObjectPersistor;
import rnd.op.dnap.DNAPJDObjectPersistor;

public abstract class AbstractModuleHandler implements ModuleHandler {

	private ObjectPersistor op;
	private ModuleHandlerDelegate delegate = new ModuleHandlerDelegate(this);

	public AbstractModuleHandler() {
		op = new DNAPJDObjectPersistor();
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
	public <T> T findObject(Object id, Class<T> objType) {
		return delegate.findObject(id, objType);
	}

	@Override
	public <T> T saveObject(T object) {
		return delegate.saveObject(object);
	}

}
