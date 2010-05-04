package rnd.mywt.client.bean;

import java.util.AbstractList;

public final class BeanPropertyList extends AbstractList implements _BoundList {

	private _BoundBean bean;

	private String listPropertyName;

	// private String inversOwnerPropName;

	public BeanPropertyList(_BoundBean bean, String listPropertyName) {
		this(bean, listPropertyName, null);
	}

	public BeanPropertyList(_BoundBean bean, String listPropertyName, String inversOwnerPropName) {
		this.bean = bean;
		this.listPropertyName = listPropertyName;
		// this.inversOwnerPropName = inversOwnerPropName;
	}

	@Override
	public Object get(int index) {
		return this.bean.getElement(this.listPropertyName, index);
	}

	@Override
	public int size() {
		return this.bean.size(this.listPropertyName);
	}

	@Override
	public void add(int index, Object element) {
		this.bean.addElement(this.listPropertyName, index, element);
		// if (this.inversOwnerPropName != null) {
		// ((_Bean) element).setValue(this.inversOwnerPropName, this.bean);
		// }
	}

	@Override
	public Object remove(int index) {
		Object oldValue = this.bean.removeElement(this.listPropertyName, index);
		// if (this.inversOwnerPropName != null) {
		// ((_Bean) oldValue).setValue(this.inversOwnerPropName, null);
		// }
		return oldValue;
	}

	@Override
	public Object set(int index, Object element) {
		// if (this.inversOwnerPropName != null) {
		// ((_Bean) element).setValue(this.inversOwnerPropName, this.bean);
		// }
		Object oldValue = this.bean.setElement(this.listPropertyName, index, element);
		// if (this.inversOwnerPropName != null) {
		// ((_Bean) oldValue).setValue(this.inversOwnerPropName, null);
		// }
		return oldValue;
	}

	public void addValueChangeListner(ValueChangeListener vcl) {
		this.bean.addValueChangeListener(this.listPropertyName, vcl);
	}

	public void removeValueChangeListner(ValueChangeListener vcl) {
		this.bean.removeValueChangeListener(this.listPropertyName, vcl);
	}
}