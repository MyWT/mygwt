package rnd.mywt.server.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rnd.bean.ApplicationBean;
import rnd.bean.ApplicationDynaBean;
import rnd.mywt.client.utils.Block;
import rnd.mywt.client.utils.ExceptionUtils;

public final class AppBeanUtils {

	private static BeanCopyCtx serverCopyBeanCtx = new ServerBeanCopyCtx();
	private static BeanCopyCtx clientBeanCopyCtx = new ClientBeanCopyCtx();

	public static BeanCopyCtx getServerCopyBeanCtx() {
		return serverCopyBeanCtx;
	}

	public static BeanCopyCtx getClientBeanCopyCtx() {
		return clientBeanCopyCtx;
	}

	public static Collection<ApplicationBean> copyAll(Collection<ApplicationBean> sourceBeans, BeanCopyCtx sourceBeanCopyCtx, BeanCopyCtx targetBeanCopyCtx) {

		Collection<ApplicationBean> targetBeans = getNewInstance(sourceBeans.getClass());
		for (ApplicationBean sourceBean : sourceBeans) {
			targetBeans.add(copy(sourceBean, sourceBeanCopyCtx, targetBeanCopyCtx));
		}
		return targetBeans;
	}

	public static ApplicationBean copy(ApplicationBean sourceBean, BeanCopyCtx sourceBeanCopyCtx, BeanCopyCtx targetBeanCopyCtx) {
		return copy(sourceBean, null, sourceBeanCopyCtx, targetBeanCopyCtx);
	}

	public static ApplicationBean copy(ApplicationBean sourceBean, ApplicationBean targetBean, BeanCopyCtx sourceBeanCopyCtx, BeanCopyCtx targetBeanCopyCtx) {
		return copy(sourceBean, targetBean, sourceBeanCopyCtx, targetBeanCopyCtx, new HashMap());
	}

	private static ApplicationBean copy(ApplicationBean sourceBean, ApplicationBean targetBean, BeanCopyCtx sourceBeanCopyCtx, BeanCopyCtx targetBeanCopyCtx, Map<ApplicationBean, ApplicationBean> copyMap) {

		// Returing to avoid in-finite loop in Object Graph
		ApplicationBean tempTargetBean = copyMap.get(sourceBean);
		if (tempTargetBean != null) {
			return tempTargetBean;
		}

		if (targetBean == null) {
			targetBean = sourceBeanCopyCtx.getNewTargetApplicationBean(sourceBean.getClassName());
		}

		copyMap.put(sourceBean, targetBean);

		// Copy ApplicationBean Id
		targetBean.setId(sourceBean.getId());

		// Copy properties
		Set<String> propertyNames = sourceBean.getPropertyNames();

		// Copy properties
		for (String propertyName : propertyNames) {

			Object value = sourceBean.getValue(propertyName);

			if (value instanceof ApplicationBean) {
				value = copy((ApplicationBean) value, null, sourceBeanCopyCtx, targetBeanCopyCtx, copyMap);
			}

			if (!propertyName.equals("ClassName")) {
				targetBean.setValue(propertyName, value);
			}
		}

		// Copy indexed properties
		Set<String> indexedPropertyNames = sourceBean.getIndexedPropertyNames();

		for (String indexedPropertyName : indexedPropertyNames) {

			int srcSize = sourceBean.size(indexedPropertyName);
			int trgtSize = targetBean.size(indexedPropertyName);

			String inverseOwner = targetBeanCopyCtx.getInverseOwner(targetBean.getClass(), indexedPropertyName);
			// D.println("inverseOwner", inverseOwner);

			Map<Long, ApplicationBean> existingTargetElementMap = new HashMap();

			for (int i = 0; i < trgtSize; i++) {

				ApplicationBean targetElement = (ApplicationBean) targetBean.getElement(indexedPropertyName, i);
				Long targetElementId = targetElement.getId();

				if (targetElementId != null) {
					existingTargetElementMap.put(targetElementId, targetElement);
				}
			}
			// D.println("targetElementMap_size", existingTargetElementMap.size());

			List<ApplicationBean> newElements = new ArrayList<ApplicationBean>();

			for (int i = 0; i < srcSize; i++) {

				ApplicationBean sourceElement = (ApplicationBean) sourceBean.getElement(indexedPropertyName, i);
				// D.println("sourceElement", sourceElement);

				Long sourceElementId = sourceElement.getId();

				ApplicationBean targetElement = null;
				boolean newElement = false;
				if (sourceElementId != null) {
					targetElement = existingTargetElementMap.remove(sourceElementId);
				} else {
					newElement = true;
				}

				targetElement = copy(sourceElement, targetElement, sourceBeanCopyCtx, targetBeanCopyCtx, copyMap);
				if (newElement) {
					newElements.add(targetElement);
				}

				// D.println("InverseOwnerRequired", targetBeanCopyHelper.isInverseOwnerRequired());
				if (targetBeanCopyCtx.isInverseOwnerRequired()) {
					targetElement.setValue(inverseOwner, targetBean);
				}
			}
			// D.println("newElements_size", newElements.size());
			if (newElements.size() > 0) {
				targetBean.addAllElement(indexedPropertyName, newElements);
			}

			Collection<ApplicationBean> existingElements = existingTargetElementMap.values();

			// D.println("existingElements_size", existingElements.size());
			if (existingElements.size() > 0) {
				targetBean.removeAllElement(indexedPropertyName, existingElements);
			}
		}

		return targetBean;
	}

	public static Class loadClass(final String className) {
		final Class clazz = (Class) ExceptionUtils.executeUnchecked(new Block() {
			public Object execute() throws Throwable {
				return Class.forName(className);
			}
		});
		return clazz;
	}

	public static <T> T getNewInstance(final Class<T> beanType) {
		return (T) ExceptionUtils.executeUnchecked(new Block() {
			public Object execute() throws Throwable {
				return beanType.newInstance();
			}
		});
	}

	public interface BeanCopyCtx {

		boolean isInverseOwnerRequired();

		String getInverseOwner(Class elementType, String indexedPrpName);

		ApplicationBean getNewTargetApplicationBean(String sourceBeanTypeName);

	}

	public static class ClientBeanCopyCtx implements BeanCopyCtx {

		public boolean isInverseOwnerRequired() {
			return false;
		}

		public String getInverseOwner(Class elementType, String indexedPrpName) {
			return null;
		}

		@Override
		public ApplicationBean getNewTargetApplicationBean(String clientBeanTypeName) {
			return getNewInstance(loadClass(getServerBeanTypeName(clientBeanTypeName)));
		}

	}

	public static String getServerBeanTypeName(String clientBeanTypeName) {
		return clientBeanTypeName.replace("client", "server");
	}

	public static class ServerBeanCopyCtx implements BeanCopyCtx {

		public boolean isInverseOwnerRequired() {
			return true;
		}

		public String getInverseOwner(Class beanType, String indexedPrpName) {
			return beanType.getSimpleName();
		}

		@Override
		public ApplicationBean getNewTargetApplicationBean(String serverBeanTypeName) {

			String clientBeanTypeName = getClientBeanTypeName(serverBeanTypeName);
			Class<ApplicationBean> clientBeanType = null;
			try {
				clientBeanType = loadClass(clientBeanTypeName);
			} catch (Exception e) {
				//e.printStackTrace();
			}

			if (clientBeanType == null) {
				return new ApplicationDynaBean(clientBeanTypeName);
			} else {
				return getNewInstance(clientBeanType);
			}
		}
	}

	public static String getClientBeanTypeName(String serverBeanTypeName) {
		return serverBeanTypeName.replace("server", "client");
	}

}
