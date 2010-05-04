package rnd.mywt.client.bean;

public class ApplicationDynaBean extends DynaConstrainedBean implements ApplicationBean {

	public Long getApplicationBeanId() {
		return (Long) getValue(APPLICATION_BEAN_ID);
	}

	public void setApplicationBeanId(Long id) {
		setValue(APPLICATION_BEAN_ID, id);
	}

	@Override
	public String toString() {
		return super.toString() + "-" + getApplicationBeanId();
	}
}