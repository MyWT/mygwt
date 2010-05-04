/**
 * 
 */
package rnd.mywt.client.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class VetoableChangeSupport {

	private Object source;

	private Vector listners;

	private Map childern;

	public VetoableChangeSupport(Object source) {
		this.source = source;
	}

	public Object getSource() {
		return this.source;
	}

	// Add / Remove Listener

	public void addVetoableChangeListener(VetoableChangeListener vcl) {
		getListeners().add(vcl);
	}

	public void removeVetoableChangeListener(VetoableChangeListener vcl) {
		if (this.listners == null) { return; }
		getListeners().remove(vcl);
	}

	// Add/ Remove Child Listener

	public void addVetoableChangeListener(String propertyName, VetoableChangeListener vcl) {
		getChild(propertyName, true).addVetoableChangeListener(vcl);
	}

	public void removeVetoableChangeListener(String propertyName, VetoableChangeListener vcl) {
		VetoableChangeSupport childVCS = getChild(propertyName, false);
		if (childVCS != null) {
			childVCS.removeVetoableChangeListener(vcl);
		}
	}

	// Fire All Listener

	protected void fireVetoableChange(String propertyName, Object oldValue, Object newValue) {
		ValueChangeEvent vce = new ValueChangeEvent(this.source, propertyName, oldValue, newValue);
		// fireVetoableChange(vce);
		fireChildVetoableChange(propertyName, vce);
	}

	protected void fireIndexedVetoableChange(String propertyName, Object oldValue, Object newValue, int index) {
		IndexedValueChangedEvent ivce = new IndexedValueChangedEvent(this.source, propertyName, oldValue, newValue, index);
		// fireVetoableChange(ivce);
		fireChildIndexedVetoableChange(propertyName, ivce);
	}

	// Fire Child Listener

	private void fireChildVetoableChange(String propertyName, ValueChangeEvent vce) {
		VetoableChangeSupport childVCS = getChild(propertyName, false);
		if (childVCS != null) {
			childVCS.fireVetoableChange(vce);
		}
	}
	
	private void fireChildIndexedVetoableChange(String propertyName, IndexedValueChangedEvent vce) {
		VetoableChangeSupport childVCS = getChild(propertyName, false);
		if (childVCS != null) {
			childVCS.fireIndexedVetoableChange(vce);
		}
	}

	// Fire Listener

	private void fireVetoableChange(ValueChangeEvent vce) {
		if (this.listners != null) {
			Iterator listenerListIter = this.listners.iterator();
			while (listenerListIter.hasNext()) {
				VetoableChangeListener vcl = (VetoableChangeListener) listenerListIter.next();
				vcl.vetoableChanged(vce);
			}
		}
	}
	
	private void fireIndexedVetoableChange(IndexedValueChangedEvent vce) {
		if (this.listners != null) {
			Iterator listenerListIter = this.listners.iterator();
			while (listenerListIter.hasNext()) {
				VetoableChangeListener vcl = (VetoableChangeListener) listenerListIter.next();
				vcl.indexedVetoableChanged(vce);
			}
		}
	}

	private VetoableChangeSupport getChild(String propertyName, boolean create) {
		if (this.listners != null) {
			VetoableChangeSupport childVCS = (VetoableChangeSupport) getChildern().get(propertyName);
			if (childVCS == null) {
				if (create) {
					childVCS = new VetoableChangeSupport(this.source);
					this.childern.put(propertyName, childVCS);
				}
			}

			return childVCS;
		}
		return null;
	}

	private Map getChildern() {
		if (this.childern == null) {
			this.childern = new HashMap();
		}
		return this.childern;
	}

	private Vector getListeners() {
		if (this.listners == null) {
			this.listners = new Vector();
		}
		return this.listners;
	}
}
