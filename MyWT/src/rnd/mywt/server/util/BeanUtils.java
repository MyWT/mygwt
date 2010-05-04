package rnd.mywt.server.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import rnd.mywt.server.bean.JavaBean;

public class BeanUtils {

	public static Object getValue(Object bean, String propertyName) {
		try {
			Method m = null;
			propertyName = capitialize(propertyName);
			try {
				m = bean.getClass().getMethod("get" + propertyName, (Class[]) null);
			} catch (NoSuchMethodException e) {
				m = bean.getClass().getMethod("is" + propertyName, (Class[]) null);
			}
			return m.invoke(bean, (Object[]) null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Object setValue(Object bean, String propertyName, Object value) {
		try {
			return bean.getClass().getMethod("set" + capitialize(propertyName), getFieldType(bean.getClass(), propertyName)).invoke(bean, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String capitialize(String propertyName) {
		char chars[] = propertyName.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars).intern();
	}

	private static Class getFieldType(Class beanClazz, String propertyName) throws NoSuchFieldException {
		do {
			// D.println("javaBeanClass", javaBeanClass);

			Field field;
			try {
				field = beanClazz.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				// D.println("continue");
				continue;
			}
			return field.getType();

		} while ((beanClazz = beanClazz.getSuperclass()) != null && beanClazz != JavaBean.class);

		throw new NoSuchFieldException(propertyName);
	}

}
