package rnd.mywt.server.bean;

import rnd.bean.ApplicationBean;

public class ApplicationJavaBean extends JavaBean implements ApplicationBean {

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getClassName() {
		return getClass().getName();
	}

	@Override
	public void setClassName(String className) {
		throw new RuntimeException("Method not supported");
	}

	@Override
	public String toString() {
		return getClassName() + "-" + getId();
	}

}
