package rnd.mywt.client.bean;

import java.util.List;

import rnd.mywt.client.utils.ObjectUtils;

public class DynaConstrainedBean extends DynaBoundBean implements _ConstrainedBean {

	private transient VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this);

	private VetoableChangeSupport getVetoableChangeSupport() {
		return this.vetoableChangeSupport;
	}

	// Add Vetoable Listener
	public void addVetoableChangeListener(VetoableChangeListener vcl) {
		getVetoableChangeSupport().addVetoableChangeListener(vcl);
	}

	public void addVetoableChangeListener(String propertyName, VetoableChangeListener vcl) {
		getVetoableChangeSupport().addVetoableChangeListener(propertyName, vcl);
	}

	// Remove Vetoable Listener
	public void removeVetoableChangeListener(VetoableChangeListener vcl) {
		getVetoableChangeSupport().removeVetoableChangeListener(vcl);
	}

	public void removeVetoableChangeListener(String propertyName, VetoableChangeListener vcl) {
		getVetoableChangeSupport().removeVetoableChangeListener(propertyName, vcl);
	}

	// Fire Vetoable Listener
	protected void fireVetoableChange(String propertyName, Object oldValue, Object newValue) {
		getVetoableChangeSupport().fireVetoableChange(propertyName, oldValue, newValue);
	}

	protected void fireIndexedVetoableChange(String propertyName, Object oldValue, Object newValue, int index) {
		getVetoableChangeSupport().fireIndexedVetoableChange(propertyName, oldValue, newValue, index);
	}

	// Property

	public Object setValue(String propertyName, Object newValue) {
		// checkListValue(newValue);

		Object oldValue = getValue(propertyName);

		// Check Equal
		if (!ObjectUtils.areEqual(oldValue, newValue)) {
			fireVetoableChange(propertyName, oldValue, newValue);
			setValue0(propertyName, newValue);
			fireValueChange(propertyName, oldValue, newValue);
		}
		return oldValue;
	}

	// Indexed Property

	public void addElement(String propertyName, int index, Object element) {
		fireIndexedVetoableChange(propertyName, null, element, index);
		getListValue(propertyName).add(index, element);
		fireIndexedValueChange(propertyName, null, element, index);
	}

	public Object removeElement(String propertyName, int index) {
//		Logger.startMethod("DynaConstrainedBean", "removeElement");
//		Logger.log("this", this);

		List listValue = getListValue(propertyName);
//		Logger.log("listValue", listValue);

		Object oldElement = listValue.get(index);
//		Logger.log("oldElement", oldElement);

		fireIndexedVetoableChange(propertyName, oldElement, null, index);
		listValue.remove(index);
		fireIndexedValueChange(propertyName, oldElement, null, index);

//		Logger.endMethod("DynaConstrainedBean", "removeElement");
		return oldElement;
	}

	public Object setElement(String propertyName, int index, Object newElement) {
		List listValue = getListValue(propertyName);
		Object oldElement = listValue.get(index);
		fireIndexedVetoableChange(propertyName, oldElement, newElement, index);
		listValue.set(index, newElement);
		fireIndexedValueChange(propertyName, oldElement, newElement, index);
		return oldElement;
	}

}
