package rnd.mywt.client.bean;

public class ApplicationDynaBean extends DynaConstrainedBean implements ApplicationBean {

	private static final String CLASS_NAME = "ClassName";

	public ApplicationDynaBean() {
	}

	public ApplicationDynaBean(String className) {
		setClassName(className);
	}

	public Long getApplicationBeanId() {
		return (Long) getValue(APPLICATION_BEAN_ID);
	}

	public void setApplicationBeanId(Long id) {
		setValue(APPLICATION_BEAN_ID, id);
	}

	public String getClassName() {
		return (String) getValue(CLASS_NAME);
	}

	public void setClassName(String className) {
		setValue(CLASS_NAME, className);
	}

	@Override
	public String toString() {
		return super.toString() + "-" + getApplicationBeanId();
	}
}