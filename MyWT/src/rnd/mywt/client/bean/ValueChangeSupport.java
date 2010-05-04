package rnd.mywt.client.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class ValueChangeSupport {

	private Object source;

	private Vector<ValueChangeListener> listeners;

	private Map<String, ValueChangeSupport> childern;

	public ValueChangeSupport(Object source) {
		this.source = source;
	}

	public Object getSource() {
		return this.source;
	}

	// Add / Remove Listener

	private void addValueChangeListener(ValueChangeListener vcl) {
		getListeners().add(vcl);
	}

	private void removeValueChangeListener(ValueChangeListener vcl) {
		if (this.listeners == null) {
			return;
		}
		getListeners().remove(vcl);
	}

	// Add/ Remove Child Listener

	public void addValueChangeListener(String propertyName, ValueChangeListener vcl) {
		getChild(propertyName, true).addValueChangeListener(vcl);
	}

	public void removeValueChangeListener(String propertyName, ValueChangeListener vcl) {
		ValueChangeSupport childVCS = getChild(propertyName, false);
		if (childVCS != null) {
			childVCS.removeValueChangeListener(vcl);
		}
	}

	// Fire All Listener

	public void fireValueChange(String propertyName, Object oldValue, Object newValue) {
		ValueChangeEvent vce = new ValueChangeEvent(this.source, propertyName, oldValue, newValue);
		// fireValueChange(vce);
		fireChildValueChange(propertyName, vce);
	}

	public void fireIndexedValueChange(String propertyName, Object oldValue, Object newValue, int index) {
		IndexedValueChangedEvent ivce = new IndexedValueChangedEvent(this.source, propertyName, oldValue, newValue, index);
		// fireIndexedValueChange(ivce);
		fireChildIndexedValueChange(propertyName, ivce);
	}

	// Fire Child Listener

	private void fireChildValueChange(String propertyName, ValueChangeEvent vce) {
		// Logger.startMethod("ValueChangeSupport", "fireChildValueChange");
		// Logger.log("propertyName", propertyName);

		ValueChangeSupport childVCS = getChild(propertyName, false);
		// Logger.log("childVCS", childVCS);

		if (childVCS != null) {
			childVCS.fireValueChange(vce);
		}
		// Logger.endMethod("ValueChangeSupport", "fireChildValueChange");
	}

	private void fireChildIndexedValueChange(String propertyName, IndexedValueChangedEvent ivce) {
		ValueChangeSupport childVCS = getChild(propertyName, false);
		if (childVCS != null) {
			childVCS.fireIndexedValueChange(ivce);
		}
	}

	// Fire Listener

	private void fireValueChange(ValueChangeEvent vce) {
		// Logger.startMethod("ValueChangeSupport", "fireValueChange");
		if (this.listeners == null) {
			return;
		}
		// Logger.log("listeners", getListeners(false));
		Iterator listenerListIter = getListeners().iterator();
		while (listenerListIter.hasNext()) {
			ValueChangeListener vcl = (ValueChangeListener) listenerListIter.next();
			vcl.valueChanged(vce);
		}
		// Logger.endMethod("ValueChangeSupport", "fireValueChange");
	}

	private void fireIndexedValueChange(IndexedValueChangedEvent ivce) {
		if (this.listeners == null) {
			return;
		}
		Iterator<ValueChangeListener> listenerListIter = getListeners().iterator();
		while (listenerListIter.hasNext()) {
			ValueChangeListener vcl = listenerListIter.next();
			vcl.indexedValueChanged(ivce);
		}
	}

	private ValueChangeSupport getChild(String propertyName, boolean create) {
		if (!create && this.childern == null) {
			return null;
		}
		ValueChangeSupport oldChildVCS = getChildern(create).get(propertyName);
		if (oldChildVCS == null) {
			if (create) {
				oldChildVCS = new ValueChangeSupport(this.source);
				this.childern.put(propertyName, oldChildVCS);
			}
		}
		return oldChildVCS;
	}

	private Map<String, ValueChangeSupport> getChildern(boolean create) {
		if (create && this.childern == null) {
			this.childern = new HashMap<String, ValueChangeSupport>();
		}
		return this.childern;
	}

	private Vector getListeners() {
		if (this.listeners == null) {
			this.listeners = new Vector();
		}
		return this.listeners;
	}
}
