package rnd.mywt.client.bean;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public abstract class AbstractBean implements _Bean {

	public Object getValue(String propertyName) {
		return getValue0(propertyName);
	}

	public Object setValue(String propertyName, Object value) {
		return setValue0(propertyName, value);
	}

	public boolean addAllElement(String propertyName, Collection c) {
		return getListValue(propertyName).addAll(c);
	}

	public boolean addElement(String propertyName, Object element) {
		return getListValue(propertyName).add(element);
	}

	public void addElement(String propertyName, int index, Object element) {
		getListValue(propertyName).add(index, element);
	}

	public Object getElement(String propertyName, int index) {
		return getListValue(propertyName).get(index);
	}

	public boolean removeAllElement(String propertyName, Collection c) {
		List value = getListValue(propertyName);
		if (c == null) {
			value.clear();
			return true;
		}
		return value.removeAll(c);
	}

	public boolean removeElement(String propertyName, Object element) {
		return getListValue(propertyName).remove(element);
	}

	public Object removeElement(String propertyName, int index) {
		return getListValue(propertyName).remove(index);
	}

	public Object setElement(String propertyName, int index, Object element) {
		return getListValue(propertyName).set(index, element);
	}

	public int size(String propertyName) {
		return getListValue(propertyName).size();
	}

	protected List getListValue(String propertyName) {
		List listValue = getListValue0(propertyName);
		if (listValue == null) {
			listValue = new LinkedList();
			setListValue0(propertyName, listValue);
		}
		return listValue;
	}

	protected List setListValue(String propertyName, Collection newListValue) {
		List listValue = getListValue(propertyName);
		listValue.clear();
		listValue.addAll(listValue);
		return listValue;
	}

	public List getListValueReadOnly(String propertyName) {
		return Collections.unmodifiableList(getListValue(propertyName));
	}

	protected abstract Object getValue0(String propertyName);

	protected abstract Object setValue0(String propertyName, Object newValue);

	protected abstract List getListValue0(String propertyName);

	protected abstract List setListValue0(String propertyName, List newValue);

}