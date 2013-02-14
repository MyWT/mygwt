package rnd.mywt.client.mvc;

import rnd.mywt.client.bean.impl.BoundBean;

public class AbstractMVCBean extends BoundBean implements MVCBean {

	private MVC parent;

	public Model getModel() {
		return (Model) getValue(MODEL);
	}

	public View getView() {
		return (View) getValue(VIEW);
	}

	public void setModel(Model m) {
		setValue(MODEL, m);
	}

	public void setView(View v) {
		setValue(VIEW, v);
	}

	public String getBoundTo() {
		return (String) getValue(BOUND_TO);
	}

	public void setBoundTo(String boundTo) {
		setValue(BOUND_TO, boundTo);
	}

	public MVC getParent() {
		return parent;
	}

	public void setParent(MVC parent) {
		this.parent = parent;
	}

	@Override
	public Object setValue(String propertyName, Object newValue) {
		// if (newValue instanceof MVC) { throw new IllegalArgumentException("You cannot set a MVC as a Property"); }
		return super.setValue(propertyName, newValue);
	}

}
