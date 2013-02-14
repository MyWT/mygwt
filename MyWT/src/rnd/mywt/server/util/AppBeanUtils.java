package rnd.mywt.server.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rnd.mywt.client.bean.ApplicationBean;
import rnd.mywt.client.utils.Block;
import rnd.mywt.client.utils.ExceptionUtils;

public class AppBeanUtils {

	public static void copyBean(ApplicationBean sourceBean, ApplicationBean targetBean, BeanCopyCtx sourceBeanCopyCtx, BeanCopyCtx targetBeanCopyCtx,
			Map<ApplicationBean, ApplicationBean> copyMap) {
		// if
		// (Debugger.D.pushCheck("rnd.webapp.mwt.server.application.AbstractModuleHandler.copy"))
		// {
		// Debugger.D.push(this, new Object[] { "sourceBean", sourceBean,
		// "targetBean", targetBean
		// });
		// }
		// try {
		// Returing to avoid in-finite loop in Object Graph
		if (copyMap.containsKey(sourceBean)) {
			return;
		}
		copyMap.put(sourceBean, targetBean);

		// Copy ApplicationBean Id
		targetBean.setApplicationBeanId(sourceBean.getApplicationBeanId());

		// Copy properties
		Set<String> propertyNames = sourceBean.getPropertyNames();
		// D.println("prpSize", propertyNames.size());

		// Copy simpe properties
		for (String propertyName : propertyNames) {
			// D.println("propertyName", propertyName);

			Object value = sourceBean.getValue(propertyName);
			// D.println("tempValue", value);

			if (value instanceof ApplicationBean) {

				ApplicationBean sourceValue = (ApplicationBean) value;

				// Long sourceObjectId =
				// sourceBeanCopyCtx.getApplicationBeanId(sourceValue);
				// sourceValue.setApplicationBeanId(sourceObjectId);
				// D.println("sourceObjectId", sourceObjectId);
				Long sourceObjectId = sourceValue.getApplicationBeanId();

				ApplicationBean targetValue = null;

				if (sourceObjectId != null) {
					targetValue = copyMap.get(sourceValue);
				}
				// D.println("targetValue", targetValue);

				if (targetValue == null) {
					targetValue = getNewApplicationBean(sourceBeanCopyCtx.getTargetBeanType(sourceValue.getClass()));
					// targetValue.setApplicationBeanId(sourceObjectId);
					copyBean(sourceValue, targetValue, sourceBeanCopyCtx, targetBeanCopyCtx, copyMap);
				}
				value = targetValue;
			}

			targetBean.setValue(propertyName, value);
		}

		// Copy indexed properties
		Set<String> indexedPropertyNames = sourceBean.getIndexedPropertyNames();
		// D.println("IndxPrpSize", indexedPropertyNames.size());

		for (String indexedPropertyName : indexedPropertyNames) {
			// D.println("indexedPropertyName", indexedPropertyName);

			int srcSize = sourceBean.size(indexedPropertyName);
			// D.println("size", size);

			int trgtSize = targetBean.size(indexedPropertyName);
			// D.println("trtSize", trtSize);

			String inverseOwner = targetBeanCopyCtx.getInverseOwner(targetBean.getClass(), indexedPropertyName);
			// D.println("inverseOwner", inverseOwner);

			Map<Long, ApplicationBean> existingTargetElementMap = new HashMap();

			for (int i = 0; i < trgtSize; i++) {

				ApplicationBean targetElement = (ApplicationBean) targetBean.getElement(indexedPropertyName, i);
				// Long targetElementId =
				// targetBeanCopyCtx.getApplicationBeanId(targetElement);
				// targetElement.setApplicationBeanId(targetElementId);
				Long targetElementId = targetElement.getApplicationBeanId();

				if (targetElementId != null) {
					existingTargetElementMap.put(targetElementId, targetElement);
				}
			}
			// D.println("targetElementMap_size",
			// existingTargetElementMap.size());

			List<ApplicationBean> newElements = new ArrayList<ApplicationBean>();

			for (int i = 0; i < srcSize; i++) {

				ApplicationBean sourceElement = (ApplicationBean) sourceBean.getElement(indexedPropertyName, i);
				// D.println("sourceElement", sourceElement);

				// Long sourceElementId =
				// sourceBeanCopyCtx.getApplicationBeanId(sourceElement);
				// sourceElement.setApplicationBeanId(sourceElementId);
				// D.println("sourceElementId", sourceElementId);
				Long sourceElementId = sourceElement.getApplicationBeanId();

				ApplicationBean targetElement = null;

				if (sourceElementId != null) {
					targetElement = existingTargetElementMap.remove(sourceElementId);
				} else {
					targetElement = getNewApplicationBean(sourceBeanCopyCtx.getTargetBeanType(sourceElement.getClass()));
					newElements.add(targetElement);
				}

				copyBean(sourceElement, targetElement, sourceBeanCopyCtx, targetBeanCopyCtx, copyMap);
				// D.println("InverseOwnerRequired",
				// targetBeanCopyHelper.isInverseOwnerRequired());

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
		// }
		// finally {
		// Debugger.D.pop("rnd.webapp.mwt.server.application.AbstractModuleHandler.copy");
		// }

	}

	public static Class getClientBeanType(Class serverBeanType) {
		return getTargetBeanType(serverBeanType, "server", "client");
	}

	public static Class getServerBeanType(Class clientBeanType) {
		return getTargetBeanType(clientBeanType, "client", "server");
	}

	private static Class getTargetBeanType(Class srcBeanType, String srcType, String trgtType) {

		String srcBeanTypeName = srcBeanType.getName();

		final String trgtClassName = srcBeanTypeName.substring(0, srcBeanTypeName.lastIndexOf('.') - srcType.length()) + trgtType + srcBeanTypeName.substring(srcBeanTypeName.lastIndexOf('.'));

		final Class targetClass = (Class) ExceptionUtils.executeUnchecked(new Block() {
			public Object execute() throws Throwable {
				return Class.forName(trgtClassName);
			}
		});

		return targetClass;
	}

	public static ApplicationBean getNewApplicationBean(final Class beanType) {
		return (ApplicationBean) ExceptionUtils.executeUnchecked(new Block() {
			public Object execute() throws Throwable {
				return beanType.newInstance();
			}
		});
	}

	public static ApplicationBean getNewClientBean(Class beanType) {
		return getNewApplicationBean(getClientBeanType(beanType));
	}

	public static class ClientBeanCopyCtx implements BeanCopyCtx {

		// public Long getApplicationBeanId(ApplicationBean clientBean) {
		// return clientBean.getApplicationBeanId();
		// }

		public boolean isInverseOwnerRequired() {
			return false;
		}

		public String getInverseOwner(Class elementType, String indexedPrpName) {
			return null;
		}

		@Override
		public Class getTargetBeanType(Class sourceBeanType) {
			return getServerBeanType(sourceBeanType);
		}
	}

	public static class ServerBeanCopyCtx implements BeanCopyCtx {

		// public Long getApplicationBeanId(ApplicationBean serverBean) {
		// return null;
		// }

		public boolean isInverseOwnerRequired() {
			return true;
		}

		public String getInverseOwner(Class beanType, String indexedPrpName) {
			// return getORMHelper().getInverseOwner(beanType, indexedPrpName);
			return beanType.getSimpleName();
		}

		@Override
		public Class getTargetBeanType(Class sourceBeanType) {
			return getClientBeanType(sourceBeanType);
		}
	}

	public interface BeanCopyCtx {

		// Long getApplicationBeanId(ApplicationBean applicationBean);

		boolean isInverseOwnerRequired();

		String getInverseOwner(Class elementType, String indexedPrpName);

		Class getTargetBeanType(Class sourceBeanType);

	}

}
