package rnd.mywt.server.bean;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rnd.bean.AbstractBean;

public class JavaBean extends AbstractBean {

	private Set propertyNames;

	private Set indexedPropertyNames;

	public Set getIndexedPropertyNames() {
		if (this.indexedPropertyNames == null) {
			populatePropertyNames();
		}
		return this.indexedPropertyNames;
	}

	public Set getPropertyNames() {
		if (this.propertyNames == null) {
			populatePropertyNames();
		}
		return this.propertyNames;
	}

	protected Object getValue0(String propertyName) {
		return JavaBeanUtils.getValue(this, propertyName);
	}

	protected Object setValue0(String propertyName, Object value) {
		return JavaBeanUtils.setValue(this, propertyName, value);
	}

	@Override
	protected final List getListValue0(String propertyName) {
		return (List) getValue(propertyName);
	}

	@Override
	protected final List setListValue0(String propertyName, List newValue) {
		return (List) setValue0(propertyName, newValue);
	}

	private void populatePropertyNames() {

		this.indexedPropertyNames = new HashSet();
		this.propertyNames = new HashSet();

		Method[] methods = getClass().getDeclaredMethods();

		for (Method method : methods) {

			Class fieldType = method.getReturnType();
			String prpName = null;
			if (method.getName().startsWith("get")) {
				prpName = getPropertyName(method, 3);
				if (fieldType.isAssignableFrom(List.class)) {
					this.indexedPropertyNames.add(prpName);
				} else {
					this.propertyNames.add(prpName);
				}
			} else if (method.getName().startsWith("is")) {
				prpName = getPropertyName(method, 2);
				this.propertyNames.add(prpName);
			}
		}
	}

	private static String getPropertyName(Method method, int i) {
		return decapitialize(method.getName().substring(i));
	}

	private static String decapitialize(String propertyName) {
		char chars[] = propertyName.toCharArray();
		chars[0] = Character.toLowerCase(chars[0]);
		return new String(chars).intern();
	}

}
