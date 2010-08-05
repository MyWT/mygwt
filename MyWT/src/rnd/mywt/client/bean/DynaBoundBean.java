package rnd.mywt.client.bean;

import rnd.mywt.client.utils.ObjectUtils;

public class DynaBoundBean extends DynaBean implements _BoundBean {

	private transient ValueChangeSupport valueChangeSupport = new ValueChangeSupport(this);

	private ValueChangeSupport getValueChangeSupport() {
		return this.valueChangeSupport;
	}

	// Add Listener

	public void addValueChangeListener(String propertyName, ValueChangeListener vcl) {
		// Logger.startMethod("DynaBoundBean", "addValueChangeListener");
		getValueChangeSupport().addValueChangeListener(propertyName, vcl);
		// Logger.endMethod("DynaBoundBean", "addValueChangeListener");
	}

	// Remove Listener

	public void removeValueChangeListener(String propertyName, ValueChangeListener vcl) {
		getValueChangeSupport().removeValueChangeListener(propertyName, vcl);
	}

	// Fire Listener
	protected void fireValueChange(String propertyName, Object oldValue, Object newValue) {
		// Logger.startMethod("DynaBoundBean", "fireValueChange");
		getValueChangeSupport().fireValueChange(propertyName, oldValue, newValue);
		// Logger.endMethod("DynaBoundBean", "fireValueChange");
	}

	protected void fireIndexedValueChange(String propertyName, Object oldValue, Object newValue, int index) {
		getValueChangeSupport().fireIndexedValueChange(propertyName, oldValue, newValue, index);
	}

	// Property

	public Object setValue(String propertyName, Object newValue) {

		Object oldValue = getValue(propertyName);

		// Check Equal
		if (ObjectUtils.areEqual(oldValue, newValue)) { return oldValue; }

		setValue0(propertyName, newValue);
		fireValueChange(propertyName, oldValue, newValue);
		return oldValue;
	}

	// Indexed Property

	public void addElement(String propertyName, int index, Object element) {
		getListValue(propertyName).add(index, element);
		fireIndexedValueChange(propertyName, null, element, index);
	}

	public Object removeElement(String propertyName, int index) {
		Object element = getListValue(propertyName).remove(index);
		fireIndexedValueChange(propertyName, element, null, index);
		return element;
	}

	public Object setElement(String propertyName, int index, Object newElement) {
		Object oldElement = getListValue(propertyName).set(index, newElement);
		fireIndexedValueChange(propertyName, oldElement, newElement, index);
		return oldElement;
	}

}
